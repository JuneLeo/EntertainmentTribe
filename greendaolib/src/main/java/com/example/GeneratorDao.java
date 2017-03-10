package com.example;

import org.greenrobot.greendao.generator.DaoGenerator;
import org.greenrobot.greendao.generator.Entity;
import org.greenrobot.greendao.generator.Schema;

import java.io.IOException;

public class GeneratorDao {
    public static final int version = 1;
    public static final String entiryPackageName = "com.entertainment.project.greendao.entiry";

    public static final String daoPackageName = "com.entertainment.project.greendao.dao";//指定dao层模板的包

    public static final String autoGenerateJavePath = "G:\\bighero\\EntertainmentTribe\\app\\src\\main\\java";

//    public static final String entityClassName = "FileAsy";
//    public static final String daoClassName = "FileAsyDao";
//    public static final String tableName = "FileAsy";
    public static final String dbName = "EntertainmentTribe";


    public static void main(String[] args) throws Exception {

        Schema schema = new Schema(version, entiryPackageName);
        schema.setDefaultJavaPackageDao(daoPackageName);
        // 模式（Schema）同时也拥有两个默认的 flags，分别用来标示 entity 是否是 activie 以及是否使用 keep sections。
        schema.enableActiveEntitiesByDefault();
        schema.enableKeepSectionsByDefault();

        addLocalFile(schema, "FileDetail", "FileDetailDao");


    }

    private static void addLocalFile(Schema schema, String entityClassName, String daoClassName) throws Exception {

        Entity entity = schema.addEntity(entityClassName);
        entity.setDbName(dbName);
        entity.addIdProperty();
        entity.addLongProperty("fileId");
        entity.addStringProperty("fileName");
        entity.addStringProperty("fileAbs");
        entity.addLongProperty("fileSize");
        entity.addIntProperty("fileType");
        entity.addIntProperty("fileFrom");
        entity.addIntProperty("fileBillType");
        entity.addStringProperty("fileUrl");
        entity.addDateProperty("createTime");
        entity.addBooleanProperty("isSelect");
        entity.setClassNameDao(daoClassName);
        //        entity.setTableName(tableName);

        new DaoGenerator().generateAll(schema, autoGenerateJavePath);

    }

}
