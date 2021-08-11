package ${PACKAGE_NAME};

import com.gzeinnumer.mylibsimplesqlite.SQLiteLIB;
import com.gzeinnumer.mylibsimplesqlite.struck.JoinColumn;
import com.gzeinnumer.mylibsimplesqlite.struck.SQLiteTable;
import com.gzeinnumer.mylibsimplesqlite.typeData.DecimalTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.IntegerTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.PrimaryKeyTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.TextTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.TimeStampTypeData;
import com.gzeinnumer.mylibsimplesqlite.typeData.VarcharTypeData;

import java.util.List;

/**
    read more on MyLibSimpleSQLite
    https://github.com/gzeinnumer/MyLibSimpleSQLite
*/
@SQLiteTable(tableName = "${TABLE_NAME}")
public class ${CAP_NAME} extends SQLiteLIB<${CAP_NAME}> {
    @PrimaryKeyTypeData private int id;
    @VarcharTypeData    private String name;

    public ${CAP_NAME}() {}

    //INSERT INTO ${TABLE_NAME} (name) VALUES ('Zein');
    public boolean insert() {
        ${CAP_NAME} data = new ${CAP_NAME}();
        data.setName("Zein");

        return insertData(${CAP_NAME}.class, GblVariabel.myDb, data);
    }

    //UPDATE ${TABLE_NAME} SET name='Name Update' WHERE id='1';
    public boolean update() {
        //set your value to update
        ${CAP_NAME} data = new ${CAP_NAME}();
        data.setName("Name Update");

        String condition = "WHERE id='1'";

        String[] fieldToUpdate = new String[]{
                "name"
        }; // put all field that you want to update

        return updatedData(${CAP_NAME}.class, GblVariabel.myDb, data, condition, fieldToUpdate);  // return true/false
    }

    //DELETE FROM ${TABLE_NAME} WHERE id='1';
    public boolean delete() {
        String condition = "WHERE id='1'";
        return deleteData(${CAP_NAME}.class, GblVariabel.myDb, condition);
    }

    //type 1 SELECT COUNT(*) FROM ${TABLE_NAME};
    public int count() {
        return countData(${CAP_NAME}.class, GblVariabel.myDb);
    }

    //type 2 SELECT COUNT(*) FROM ${TABLE_NAME} WHERE id='1';
    public int count2() {
        String condition = "WHERE id='1'";
        return countData(${CAP_NAME}.class, GblVariabel.myDb, condition);
    }

    // SELECT COUNT(id) FROM ${TABLE_NAME};
    public int queryCount() {
        String query = "SELECT COUNT(id) FROM ${TABLE_NAME};";
        return queryCount(${CAP_NAME}.class, GblVariabel.myDb, query);
    }

    //type 1 SElECT * FROM ${TABLE_NAME};
    public List<${CAP_NAME}> read() {
        return readData(${CAP_NAME}.class, GblVariabel.myDb);
    }

    //type 2 SELECT * FROM ${TABLE_NAME} WHERE id='1';
    public List<${CAP_NAME}> read2() {
        String condition = "WHERE flag_active='1'";

        return readData(${CAP_NAME}.class, GblVariabel.myDb, condition);
    }

    public List<${CAP_NAME}> query(){
        String query ="SELECT ${TABLE_NAME}.* FROM table1;";
        return queryData(${CAP_NAME}.class, GblVariabel.myDb, query);
    }

    public boolean queryResultUpdate() {
        String query = "UPDATE ${TABLE_NAME} SET name='name new' WHERE id='1'";
        return queryResult(${CAP_NAME}.class, GblVariabel.myDb, query);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
