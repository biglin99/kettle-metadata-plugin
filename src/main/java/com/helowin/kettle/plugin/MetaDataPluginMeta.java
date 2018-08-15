package com.helowin.kettle.plugin;

import org.eclipse.swt.widgets.Shell;
import org.pentaho.di.core.CheckResult;
import org.pentaho.di.core.CheckResultInterface;
import org.pentaho.di.core.Const;
import org.pentaho.di.core.Counter;
import org.pentaho.di.core.database.DatabaseMeta;
import org.pentaho.di.core.exception.KettleValueException;
import org.pentaho.di.core.exception.KettleXMLException;
import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.core.variables.VariableSpace;
import org.pentaho.di.core.xml.XMLHandler;
import org.pentaho.di.repository.Repository;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.*;
import org.pentaho.metastore.api.IMetaStore;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Map;

/**
 * @Title: 元数据类
 */
public class MetaDataPluginMeta extends BaseStepMeta implements StepMetaInterface {

    private static Class<?> PKG = MetaDataPluginMeta.class; // for i18n purposes


    private String ipString = "";
    private String portString = "";

    private String metaIndexDbNameString = "";   //元数据索引表所在数据
    private String metaDataDbNameString = "";    //元数据具体值域所在数据库，鹏飞那边

    private String userNameString = "";
    private String passwordString = "";

    private String inputDbNameString = "";     //输入表所在的数据源名称
    private String outputDbNameString = "";     //输出表所在的数据源名称

    private String inputTableNameString = "";   //输入表名

    @Override
    public void setDefault() {

        ipString = "192.168.99.36";
        portString = "3306";

        userNameString = "root";
        passwordString = "hellowin.3edc4rfv";

        metaIndexDbNameString = "data_acquisition_db";
        metaDataDbNameString = "visualize";

        inputDbNameString = "tablName";
        outputDbNameString = "tablName";

        inputTableNameString = "tableName";

    }

    @Override
    public String getXML() throws KettleValueException {
        String retval = "";
        retval += "		<ipString>" + getIpString() + "</ipString>" + Const.CR;
        retval += "		<portString>" + getPortString() + "</portString>" + Const.CR;
        retval += "		<userNameString>" + getUserNameString() + "</userNameString>" + Const.CR;
        retval += "		<passwordString>" + getPasswordString() + "</passwordString>" + Const.CR;
        retval += "		<metaIndexDbNameString>" + getMetaIndexDbNameString() + "</metaIndexDbNameString>" + Const.CR;
        retval += "		<metaDataDbNameString>" + getMetaDataDbNameString() + "</metaDataDbNameString>" + Const.CR;
        retval += "		<inputDbNameString>" + getInputDbNameString() + "</inputDbNameString>" + Const.CR;
        retval += "		<outputDbNameString>" + getInputDbNameString() + "</outputDbNameString>" + Const.CR;
        retval += "		<inputTableNameString>" + getInputTableNameString() + "</inputTableNameString>" + Const.CR;
        return retval;
    }

//	  public void getFields( RowMetaInterface row, String name, RowMetaInterface[] info, StepMeta nextStep,
//		      VariableSpace space, Repository repository, IMetaStore metaStore ) throws KettleStepException {
//		System.out.println("getFields~！~~~~~~");
//
//	  }

    @Override
    public Object clone() {
        Object retval = super.clone();
        return retval;
    }

    public void loadXML(Node stepnode, List<DatabaseMeta> databases, Map<String, Counter> counters)
            throws KettleXMLException {

        try {
            setIpString(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "ipString")));
            setPortString(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "portString")));

            setUserNameString(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "userNameString")));
            setPasswordString(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "passwordString")));

            setMetaIndexDbNameString(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "metaIndexdbNameString")));
            setMetaDataDbNameString(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "metaDataDbNameString")));

            setInputDbNameString(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "inputDbNameString")));
            setOutputDbNameString(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "outputDbNameString")));

            setInputTableNameString(XMLHandler.getNodeValue(XMLHandler.getSubNode(stepnode, "inputTableNameString")));

        } catch (Exception e) {
            throw new KettleXMLException("Meta Check Plugin Unable to read step info from XML node", e);
        }

    }

    public void check(List<CheckResultInterface> remarks, TransMeta transmeta, StepMeta stepMeta, RowMetaInterface prev,
                      String input[], String output[], RowMetaInterface info) {
        CheckResult cr;

        // See if we have input streams leading to this step!
        if (input.length > 0) {
            cr = new CheckResult(CheckResult.TYPE_RESULT_OK, "Step is receiving info from other steps.", stepMeta);
            remarks.add(cr);
        } else {
            cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, "No input received from other steps!", stepMeta);
            remarks.add(cr);
        }

    }

    public StepDialogInterface getDialog(Shell shell, StepMetaInterface meta, TransMeta transMeta, String name) {
        return new MetaDataPluginDialog(shell, meta, transMeta, name);
    }

    @Override
    public StepInterface getStep(StepMeta stepMeta, StepDataInterface stepDataInterface, int cnr, TransMeta transMeta,
                                 Trans disp) {
        return new MetaDataPlugin(stepMeta, stepDataInterface, cnr, transMeta, disp);
    }

    @Override
    public StepDataInterface getStepData() {
        return new MetaDataPluginData();
    }

    @Override
    public boolean supportsErrorHandling() {
        return true;
    }

    @Override
    public void check(List<CheckResultInterface> remarks, TransMeta transMeta, StepMeta stepMeta, RowMetaInterface prev,
                      String[] input, String[] output, RowMetaInterface info, VariableSpace space, Repository repository,
                      IMetaStore metaStore) {
        CheckResult cr;
        if (input.length > 0) {
            cr = new CheckResult(CheckResult.TYPE_RESULT_OK, "Step is receiving info from other steps.", stepMeta);
            remarks.add(cr);
        } else {
            cr = new CheckResult(CheckResult.TYPE_RESULT_ERROR, "No input received from other steps!", stepMeta);
            remarks.add(cr);
        }
    }

    public String getMetaIndexDbNameString() {
        return metaIndexDbNameString;
    }

    public void setMetaIndexDbNameString(String metaIndexDbNameString) {
        this.metaIndexDbNameString = metaIndexDbNameString;
    }

    public String getPortString() {
        return portString;
    }

    public void setPortString(String portString) {
        this.portString = portString;
    }

    public String getMetaDataDbNameString() {
        return metaDataDbNameString;
    }

    public void setMetaDataDbNameString(String metaDataDbNameString) {
        this.metaDataDbNameString = metaDataDbNameString;
    }

    public String getUserNameString() {
        return userNameString;
    }

    public void setUserNameString(String userNameString) {
        this.userNameString = userNameString;
    }

    public String getPasswordString() {
        return passwordString;
    }

    public void setPasswordString(String passwordString) {
        this.passwordString = passwordString;
    }

    public String getInputDbNameString() {
        return inputDbNameString;
    }

    public void setInputDbNameString(String inputDbNameString) {
        this.inputDbNameString = inputDbNameString;
    }

    public String getInputTableNameString() {
        return inputTableNameString;
    }

    public void setInputTableNameString(String inputTableNameString) {
        this.inputTableNameString = inputTableNameString;
    }

    public String getIpString() {
        return ipString;
    }

    public void setIpString(String ipString) {
        this.ipString = ipString;
    }

    public String getOutputDbNameString() {
        return outputDbNameString;
    }

    public void setOutputDbNameString(String outputDbNameString) {
        this.outputDbNameString = outputDbNameString;
    }
}
