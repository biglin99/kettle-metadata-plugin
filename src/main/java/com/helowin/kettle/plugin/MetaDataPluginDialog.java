package com.helowin.kettle.plugin;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.*;
import org.pentaho.di.core.Const;
import org.pentaho.di.i18n.BaseMessages;
import org.pentaho.di.trans.TransMeta;
import org.pentaho.di.trans.step.BaseStepMeta;
import org.pentaho.di.trans.step.StepDialogInterface;
import org.pentaho.di.ui.trans.step.BaseStepDialog;

/**
 * @Title: 对话框类
 */

public class MetaDataPluginDialog extends BaseStepDialog implements StepDialogInterface {

    private static Class<?> PKG = MetaDataPluginMeta.class; // for i18n purposes

    private MetaDataPluginMeta input;

    // output field name

    private Text ipText;    //ip
    private Text portText;  //端口
    private Text metaIndexDbNameText;   //元数据索引表，就是我们数据库中存表名、字段名、元数据CODE表
    private Text metaDataDbNameText;  //具体元数据所在数据库，鹏飞那边设计的
    private Text userNameText;
    private Text passwordText;
    private Text inputDbNameText;   //输入的表的数据库名称
    private Text outputDbNameText;    //输出数据库名称
    private Text inputTableNameText;   //输入表的表名


    public MetaDataPluginDialog(Shell parent, Object in, TransMeta transMeta, String sname) {
        super(parent, (BaseStepMeta) in, transMeta, sname);
        input = (MetaDataPluginMeta) in;
    }

    @Override
    public String open() {
        Shell parent = getParent();
        Display display = parent.getDisplay();

        shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.MIN | SWT.MAX);
        props.setLook(shell);
        setShellImage(shell, input);

        ModifyListener lsMod = new ModifyListener() {
            @Override
            public void modifyText(ModifyEvent e) {
                input.setChanged();
            }
        };
        changed = input.hasChanged();

        FormLayout formLayout = new FormLayout();
        formLayout.marginWidth = Const.FORM_MARGIN;
        formLayout.marginHeight = Const.FORM_MARGIN;

        shell.setLayout(formLayout);
        // i18n设置名称BaseMessages.getString(PKG, "Template.Shell.Title")
        shell.setText("数据检验插件");

        int middle = props.getMiddlePct();
        int margin = Const.MARGIN;

        // Stepname line
        wlStepname = new Label(shell, SWT.RIGHT);
        wlStepname.setText("数据检验");
        props.setLook(wlStepname);
        fdlStepname = new FormData();
        fdlStepname.left = new FormAttachment(0, 0);
        fdlStepname.right = new FormAttachment(middle, -margin);
        fdlStepname.top = new FormAttachment(0, margin);
        wlStepname.setLayoutData(fdlStepname);

        wStepname = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
        wStepname.setText("数据校验插件");
        props.setLook(wStepname);
        wStepname.addModifyListener(lsMod);
        fdStepname = new FormData();
        fdStepname.left = new FormAttachment(middle, 0);
        fdStepname.top = new FormAttachment(0, margin);
        fdStepname.right = new FormAttachment(100, 0);
        wStepname.setLayoutData(fdStepname);

        ///////
        Label lblNewLabel = new Label(shell, SWT.NONE);
        props.setLook(lblNewLabel);
        FormData fd_lblNewLabel = new FormData();
        fd_lblNewLabel.left = new FormAttachment(0, 28);
        lblNewLabel.setLayoutData(fd_lblNewLabel);
        lblNewLabel.setText("主机名");

        Label dbName = new Label(shell, SWT.NONE);
        props.setLook(dbName);
        fd_lblNewLabel.bottom = new FormAttachment(dbName, -17);
        dbName.setToolTipText("元数据索引表所在库名");
        FormData fd_dbName = new FormData();
        fd_dbName.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
        dbName.setLayoutData(fd_dbName);
        dbName.setText("数据库名");

        metaIndexDbNameText = new Text(shell, SWT.BORDER);
        props.setLook(metaIndexDbNameText);
        fd_dbName.top = new FormAttachment(0, 99);
        FormData fd_dbNameText = new FormData();
        fd_dbNameText.top = new FormAttachment(dbName, -3, SWT.TOP);
        fd_dbNameText.left = new FormAttachment(dbName, 43);
        metaIndexDbNameText.setLayoutData(fd_dbNameText);

        Label port = new Label(shell, SWT.NONE);
        props.setLook(port);
        FormData fd_port = new FormData();
        fd_port.top = new FormAttachment(lblNewLabel, 0, SWT.TOP);
        port.setLayoutData(fd_port);
        port.setText("端口");

        Label metaDataDbName = new Label(shell, SWT.NONE);
        props.setLook(metaDataDbName);
        metaDataDbName.setToolTipText("元数据所在数据库名");
        FormData fd_metaDataDbName = new FormData();
        fd_metaDataDbName.top = new FormAttachment(0, 129);
        fd_metaDataDbName.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
        metaDataDbName.setLayoutData(fd_metaDataDbName);
        metaDataDbName.setText("元数据库名");

        metaDataDbNameText = new Text(shell, SWT.BORDER);
        props.setLook(metaDataDbNameText);
        fd_dbNameText.right = new FormAttachment(metaDataDbNameText, 0, SWT.RIGHT);
        FormData fd_metaDataDbNameText = new FormData();
        fd_metaDataDbNameText.right = new FormAttachment(100, -201);
        fd_metaDataDbNameText.left = new FormAttachment(metaDataDbName, 31);
        fd_metaDataDbNameText.top = new FormAttachment(metaDataDbName, -3, SWT.TOP);
        metaDataDbNameText.setLayoutData(fd_metaDataDbNameText);

        Label inputTableName = new Label(shell, SWT.NONE);
        props.setLook(inputTableName);
        FormData fd_inputTableName = new FormData();
        fd_inputTableName.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
        inputTableName.setLayoutData(fd_inputTableName);
        inputTableName.setText("输入表明");

        Label userName = new Label(shell, SWT.NONE);
        props.setLook(userName);
        FormData fd_userName = new FormData();
        fd_userName.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
        userName.setLayoutData(fd_userName);
        userName.setText("用户名");

        userNameText = new Text(shell, SWT.BORDER);
        props.setLook(userNameText);
        fd_userName.top = new FormAttachment(userNameText, 3, SWT.TOP);
        FormData fd_userNameText = new FormData();
        fd_userNameText.right = new FormAttachment(100, -201);
        fd_userNameText.left = new FormAttachment(userName, 55);
        fd_userNameText.top = new FormAttachment(0, 160);
        userNameText.setLayoutData(fd_userNameText);

        Label password = new Label(shell, SWT.NONE);
        props.setLook(password);
        FormData fd_password = new FormData();
        fd_password.left = new FormAttachment(0, 28);
        password.setLayoutData(fd_password);
        password.setText("密码");

        passwordText = new Text(shell, SWT.BORDER);
        props.setLook(passwordText);
        fd_password.right = new FormAttachment(passwordText, -55);
        fd_password.top = new FormAttachment(0, 195);
        FormData fd_passwordText = new FormData();
        fd_passwordText.top = new FormAttachment(0, 192);
        fd_passwordText.right = new FormAttachment(100, -201);
        fd_passwordText.left = new FormAttachment(0, 119);
        passwordText.setLayoutData(fd_passwordText);

        Label inputDbName = new Label(shell, SWT.NONE);
        props.setLook(inputDbName);
        FormData fd_inputDbName = new FormData();
        fd_inputDbName.top = new FormAttachment(password, 17);
        fd_inputDbName.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
        inputDbName.setLayoutData(fd_inputDbName);
        inputDbName.setText("输入数据库名称");

        inputDbNameText = new Text(shell, SWT.BORDER);
        props.setLook(inputDbNameText);
        FormData fd_inputDbNameText = new FormData();
        fd_inputDbNameText.right = new FormAttachment(100, -203);
        fd_inputDbNameText.left = new FormAttachment(inputDbName, 7);
        fd_inputDbNameText.top = new FormAttachment(inputDbName, -3, SWT.TOP);
        inputDbNameText.setLayoutData(fd_inputDbNameText);

        inputTableNameText = new Text(shell, SWT.BORDER);
        props.setLook(inputTableNameText);
        fd_inputTableName.top = new FormAttachment(inputTableNameText, 3, SWT.TOP);
        FormData fd_inputTableNameText = new FormData();
        fd_inputTableNameText.left = new FormAttachment(metaIndexDbNameText, 0, SWT.LEFT);
        fd_inputTableNameText.right = new FormAttachment(100, -201);
        inputTableNameText.setLayoutData(fd_inputTableNameText);

        ipText = new Text(shell, SWT.BORDER);
        props.setLook(ipText);
        fd_port.left = new FormAttachment(ipText, 86);
        FormData fd_ipText = new FormData();
        fd_ipText.right = new FormAttachment(metaIndexDbNameText, 236);
        fd_ipText.top = new FormAttachment(lblNewLabel, 0, SWT.TOP);
        fd_ipText.left = new FormAttachment(metaIndexDbNameText, 0, SWT.LEFT);
        ipText.setLayoutData(fd_ipText);

        portText = new Text(shell, SWT.BORDER);
        props.setLook(portText);
        fd_port.right = new FormAttachment(portText, -13);
        FormData fd_portText = new FormData();
        fd_portText.left = new FormAttachment(0, 494);
        fd_portText.top = new FormAttachment(lblNewLabel, 0, SWT.TOP);
        portText.setLayoutData(fd_portText);

        Label outputDbName = new Label(shell, SWT.NONE);
        props.setLook(outputDbName);
        FormData fd_outputDbName = new FormData();
        fd_outputDbName.left = new FormAttachment(lblNewLabel, 0, SWT.LEFT);
        outputDbName.setLayoutData(fd_outputDbName);
        outputDbName.setText("输出数据库名称");

        outputDbNameText = new Text(shell, SWT.BORDER);
        props.setLook(outputDbNameText);
        fd_outputDbName.bottom = new FormAttachment(outputDbNameText, 2, SWT.BOTTOM);
        fd_outputDbName.top = new FormAttachment(outputDbNameText, 3, SWT.TOP);
        fd_inputTableNameText.top = new FormAttachment(outputDbNameText, 17);
        FormData fd_outputDbNameText = new FormData();
        fd_outputDbNameText.right = new FormAttachment(metaIndexDbNameText, 0, SWT.RIGHT);
        fd_outputDbNameText.top = new FormAttachment(inputDbNameText, 16);
        fd_outputDbNameText.left = new FormAttachment(metaIndexDbNameText, 0, SWT.LEFT);
        outputDbNameText.setLayoutData(fd_outputDbNameText);
        ///////

        props.setLook(ipText);
        props.setLook(portText);

        props.setLook(metaIndexDbNameText);
        props.setLook(metaDataDbNameText);

        props.setLook(userNameText);
        props.setLook(passwordText);

        props.setLook(inputDbNameText);
        props.setLook(outputDbNameText);

        props.setLook(inputTableNameText);

        ipText.addModifyListener(lsMod);
        portText.addModifyListener(lsMod);

        metaIndexDbNameText.addModifyListener(lsMod);
        metaDataDbNameText.addModifyListener(lsMod);

        userNameText.addModifyListener(lsMod);
        passwordText.addModifyListener(lsMod);

        inputDbNameText.addModifyListener(lsMod);
        outputDbNameText.addModifyListener(lsMod);

        inputTableNameText.addModifyListener(lsMod);


        // OK and cancel buttons
        wCancel = new Button(shell, SWT.NONE);
        wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel"));

        wOK = new Button(shell, SWT.NONE);
        wOK.setText(BaseMessages.getString(PKG, "System.Button.OK"));

        setButtonPositions(new Button[]{wOK, wCancel}, margin, null);


        // Add listeners
        lsCancel = new Listener() {
            @Override
            public void handleEvent(Event e) {
                cancel();
            }
        };
        lsOK = new Listener() {
            @Override
            public void handleEvent(Event e) {
                ok();
            }
        };

        wCancel.addListener(SWT.Selection, lsCancel);
        wOK.addListener(SWT.Selection, lsOK);

        lsDef = new SelectionAdapter() {
            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                ok();
            }
        };

        wStepname.addSelectionListener(lsDef);

        ipText.addSelectionListener(lsDef);
        portText.addSelectionListener(lsDef);

        metaIndexDbNameText.addSelectionListener(lsDef);
        metaDataDbNameText.addSelectionListener(lsDef);

        userNameText.addSelectionListener(lsDef);
        passwordText.addSelectionListener(lsDef);

        inputDbNameText.addSelectionListener(lsDef);
        outputDbNameText.addSelectionListener(lsDef);

        inputTableNameText.addSelectionListener(lsDef);


        // Detect X or ALT-F4 or something that kills this window...
        shell.addShellListener(new ShellAdapter() {
            @Override
            public void shellClosed(ShellEvent e) {
                cancel();
            }
        });

        // Set the shell size, based upon previous time...
        setSize(shell, 400, 300, true);

        getData();
        input.setChanged(changed);

        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        return stepname;
    }

    // Read data and place it in the dialog
    public void getData() {
        wStepname.selectAll();

        ipText.setText(input.getIpString());
        portText.setText(input.getPortString());

        metaIndexDbNameText.setText(input.getMetaIndexDbNameString());
        metaDataDbNameText.setText(input.getMetaDataDbNameString());

        userNameText.setText(input.getUserNameString());
        passwordText.setText(input.getPasswordString());

        inputDbNameText.setText(input.getInputDbNameString());
        outputDbNameText.setText(input.getInputDbNameString());

        inputTableNameText.setText(input.getInputTableNameString());

    }

    private void cancel() {
        stepname = null;
        input.setChanged(changed);
        dispose();
    }

    // let the plugin know about the entered data
    private void ok() {
        stepname = wStepname.getText(); // return value

        input.setIpString(ipText.getText());
        input.setPortString(portText.getText());

        input.setMetaIndexDbNameString(metaIndexDbNameText.getText());
        input.setMetaDataDbNameString(metaDataDbNameText.getText());

        input.setUserNameString(userNameText.getText());
        input.setPasswordString(passwordText.getText());

        input.setInputDbNameString(inputDbNameText.getText());
        input.setOutputDbNameString(outputDbNameText.getText());

        input.setInputTableNameString(inputTableNameText.getText());
        dispose();
    }
}
