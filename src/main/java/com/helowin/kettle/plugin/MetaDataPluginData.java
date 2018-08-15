package com.helowin.kettle.plugin;

/**
 * @Title: 数据类
 */

import org.pentaho.di.core.row.RowMetaInterface;
import org.pentaho.di.trans.step.BaseStepData;
import org.pentaho.di.trans.step.StepDataInterface;

public class MetaDataPluginData extends BaseStepData implements StepDataInterface {

    public RowMetaInterface outputRowMeta;

    public MetaDataPluginData() {
        super();
    }
}

