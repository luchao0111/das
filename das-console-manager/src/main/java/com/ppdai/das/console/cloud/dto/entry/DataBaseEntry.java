package com.ppdai.das.console.cloud.dto.entry;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ppdai.das.console.common.utils.DataSourceUtil;
import com.ppdai.das.console.common.validates.group.db.AddDataBase;
import com.ppdai.das.console.common.validates.group.db.DeleteDataBase;
import com.ppdai.das.console.common.validates.group.db.UpdateDataBase;
import com.ppdai.das.console.common.validates.group.groupdb.AddGroupDB;
import com.ppdai.das.console.common.validates.group.groupdb.DeleteGroupDB;
import com.ppdai.das.console.common.validates.group.groupdb.TransferGroupDB;
import com.ppdai.das.console.common.validates.group.groupdb.UpdateGroupDB;
import com.ppdai.das.console.dto.view.search.CheckTypes;
import com.ppdai.das.console.enums.DataBaseEnum;
import com.ppdai.das.console.service.SetupDataBaseService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * create by das-console
 * 请勿修改此文件
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table
public class DataBaseEntry {


    @NotNull(message = "{dalgroupdb.id.notNull}", groups = {UpdateDataBase.class, DeleteDataBase.class, AddGroupDB.class, UpdateGroupDB.class, DeleteGroupDB.class, TransferGroupDB.class})
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "{dalgroupdb.dbname.notNull}", groups = {AddDataBase.class, UpdateDataBase.class})
    @Column(name = "db_name")
    private String dbname;

    @NotNull(message = "{dalgroupdb.dal_group_id.notNull}", groups = {AddGroupDB.class, UpdateGroupDB.class, DeleteGroupDB.class, TransferGroupDB.class})
    @Column(name = "dal_group_id")
    private Long dal_group_id;

    @NotBlank(message = "{dalgroupdb.db_address.notNull}", groups = {AddDataBase.class, UpdateDataBase.class})
    @Column(name = "db_address")
    private String db_address;

    @NotBlank(message = "{dalgroupdb.db_port.notNull}", groups = {AddDataBase.class, UpdateDataBase.class})
    @Column(name = "db_port")
    private String db_port;

    @NotBlank(message = "{dalgroupdb.db_user.notNull}", groups = {AddDataBase.class, UpdateDataBase.class})
    @Column(name = "db_user")
    private String db_user;

    @NotBlank(message = "{dalgroupdb.db_password.notNull}", groups = {AddDataBase.class, UpdateDataBase.class})
    @Column(name = "db_password")
    private String db_password;

    @NotBlank(message = "{dalgroupdb.db_catalog.notNull}", groups = {AddDataBase.class, UpdateDataBase.class})
    @Column(name = "db_catalog")
    private String db_catalog;

    /**
     * 数据库类型：1、mysql 2、SqlServer
     **/
    @NotNull(message = "{dalgroupdb.db_type.notNull}", groups = {AddDataBase.class})
    @Column(name = "db_type")
    private Integer db_type;

    @Column(name = "insert_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date create_time;

    @Column(name = "comment")
    private String comment;

    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date update_time;

    /**
     * 最后操作人
     **/
    @Column(name = "update_user_no")
    private String updateUserNo;

    private boolean addToGroup;

    private boolean isGenDefault;

    private String group_name;

    @NotNull(message = "{dalgroupdb.dal_group_id.notNull}", groups = {TransferGroupDB.class})
    private Long target_dal_group_id;

    private CheckTypes db_types;

    private List<String> insert_times;

    public String getDbname() {
        if (StringUtils.isNotBlank(dbname)) {
            return dbname.trim();
        }
        return dbname;
    }

    public String getDb_catalog() {
        if (StringUtils.isNotBlank(db_catalog)) {
            return db_catalog.trim();
        }
        return db_catalog;
    }

    public void setDbname(String dbname) {
        if (StringUtils.isNotBlank(dbname)) {
            this.dbname = dbname.trim();
            return;
        }
        this.dbname = dbname;
    }

    public void setDb_catalog(String db_catalog) {
        if (StringUtils.isNotBlank(db_catalog)) {
            this.db_catalog = db_catalog.trim();
            return;
        }
        this.db_catalog = db_catalog;
    }

    public String getConnectionUrl() {
        if(DataBaseEnum.MYSQL.getType().equals(db_type)){
            return String.format(SetupDataBaseService.jdbcUrlTemplate, this.getDb_address(), this.getDb_port(), this.getDb_catalog());
        }
        return String.format(DataSourceUtil.DBURL_SQLSERVER_CACHE, this.getDb_address(), this.getDb_port(), this.getDb_catalog());
    }

    public String getDriverClassName() {
        return DataBaseEnum.getDataBaseEnumByType(db_type).getDriver();
    }

}