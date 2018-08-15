package com.helowin.kettle.plugin;

import com.helowin.db.AuditData;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.row.ValueMetaInterface;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.*;

import java.util.Arrays;
import java.util.List;

/**
 * @Title: 步骤类
 */

public class MetaDataPlugin extends BaseStep implements StepInterface {
    private MetaDataPluginData data;
    private MetaDataPluginMeta meta;

    private static AuditData auditData = AuditData.getInstance();

    private int errorCount, rightCount, totalCount;
    private String batchId;
    private String[] cloumnName;

    public MetaDataPlugin(StepMeta s, StepDataInterface stepDataInterface, int c, TransMeta t, Trans dis) {
        super(s, stepDataInterface, c, t, dis);
    }

    @Override
    public boolean processRow(StepMetaInterface smi, StepDataInterface sdi) throws KettleException {
        meta = (MetaDataPluginMeta) smi;
        data = (MetaDataPluginData) sdi;

        Object[] r = getRow(); // get row, blocks when needed!

        System.out.println(Arrays.toString(r));
        if (r == null) // no more input to be expected...
        {
            if (totalCount > 0) {
                // 执行统计输出
                logBasic("totalCount" + totalCount + ",rightcount:" + rightCount + ",errorCount:" + errorCount);
                try {
//				auditData.saveStatisInfo(r[0], inputDbName, outDbname, inputTableName, totalCount, rightCount, errorCount)
                    auditData.saveStatisInfo(batchId, meta.getInputDbNameString(), meta.getOutputDbNameString(),
                            meta.getInputTableNameString(), totalCount, rightCount, errorCount);
                } catch (Exception e) {
                    logError("插入统计信息异常！");
                }
            }
            setOutputDone();
            return false;
        }

        if (first) {
            first = false;
            batchId = (String) r[0];
            auditData.init(meta.getIpString(), meta.getPortString(), meta.getUserNameString(), meta.getPasswordString(),
                    meta.getMetaIndexDbNameString(), meta.getMetaDataDbNameString());

            data.outputRowMeta = (RowMetaInterface) getInputRowMeta().clone();
            meta.getFields(data.outputRowMeta, getStepname(), null, null, this, getRepository(), getMetaStore());
            logBasic("meta  step initialized successfully");

            List<ValueMetaInterface> list = getInputRowMeta().getValueMetaList();
            cloumnName = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                cloumnName[i] = list.get(i).getName();
            }
        }

        // 转object行记录为string类型
        String[] line = new String[cloumnName.length];
        for (int i = 0; i < cloumnName.length; i++) {
            line[i] = String.valueOf(r[i]);
        }

        // 调用check方法，
        // String inputDbName, String ip, String port,
        // String metaDatabaseName, String metaValueDatabaseName,
        // String username, String password,
        // String sourceTableName, String metaDataTableName, String[] cloumnName,
        // String[] lines
        String result = "0";
        try {
//			result = auditData.check(meta.getInputDbNameString(), meta.getIpString(), meta.getPortString(),
//					meta.getMetaIndexdbNameString(), meta.getMetaDataDbNameString(), meta.getUserNameString(),
//					meta.getPasswordString(), meta.getInputTableNameString(), "tbl_audit_rule_relation", cloumnName,
//					line);
            result = auditData.check(meta.getInputDbNameString(), meta.getInputTableNameString(), cloumnName, line);

        } catch (PoolInitializationException e) {
            logError("插件出错！数据库连接初始化失败");
        } catch (Exception e) {
            logError("校验过程出错！");
        }
        totalCount++;
        // 通过返回0，否则返回报错的字段
        if (result.equals("0")) {
            putRow(data.outputRowMeta, line);
            rightCount++;
        } else {
            // 失败，返回错误的字段名,,,错误个数，1固定，错误描述，错误编号
            putError(data.outputRowMeta, line, 1, result + "不满足要求", result, "0000");
            errorCount++;
        }

        if (checkFeedback(getLinesRead())) {
            logBasic("Linenr " + getLinesRead()); // Some basic logging
        }

        System.out.println("totalCount" + totalCount + ",rightcount:" + rightCount + ",errorCount:" + errorCount);
        return true;
    }

    @Override
    public boolean init(StepMetaInterface smi, StepDataInterface sdi) {
        meta = (MetaDataPluginMeta) smi;
        data = (MetaDataPluginData) sdi;

        return super.init(smi, sdi);
    }

    @Override
    public void dispose(StepMetaInterface smi, StepDataInterface sdi) {
        meta = (MetaDataPluginMeta) smi;
        data = (MetaDataPluginData) sdi;

        super.dispose(smi, sdi);
    }
}
