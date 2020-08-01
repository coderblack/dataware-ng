import org.apache.kudu.ColumnSchema;
import org.apache.kudu.Schema;
import org.apache.kudu.Type;
import org.apache.kudu.client.*;
import org.apache.kudu.client.KuduClient.KuduClientBuilder;

import java.util.ArrayList;
import java.util.List;

/***
 * @author hunter.d
 * @qq 657270652
 * @wx haitao-duan
 * @date 2020/8/1
 **/
public class KuduTest {

    public static final String KUDU_MASTER = "doitedu01:7051";

    public static void main(String[] args) throws Exception {
        //createTable();
        add();
    }

    /**
     * 创建表
     * @throws Exception
     */
    public static void createTable() throws Exception{
        //1、创建一个client
        KuduClient client = new KuduClientBuilder(KUDU_MASTER).build();
        //2、创建schema信息
        List<ColumnSchema> columns = new ArrayList<ColumnSchema>();
        columns.add(new ColumnSchema.ColumnSchemaBuilder("id", Type.INT32).key(true).nullable(false).build());
        columns.add(new ColumnSchema.ColumnSchemaBuilder("name", Type.STRING).key(false).nullable(false).build());
        columns.add(new ColumnSchema.ColumnSchemaBuilder("age", Type.INT32).key(false).nullable(false).build());
        Schema schema = new Schema(columns);
        //3、指定分区字段
        List<String> partions = new ArrayList<String>();
        partions.add("id");
        //4、指定分区方式为hash分区、6个分区，一个副本
        CreateTableOptions options = new CreateTableOptions().addHashPartitions(partions, 6).setNumReplicas(1);
        //5、创建表，
        client.createTable("person",schema,options);

        client.close();
    }

    /**
     * 插入数据
     * @throws Exception
     */
    public static void add() throws Exception{
        //1、创建一个client
        KuduClient client = new KuduClientBuilder(KUDU_MASTER).build();
        //2、打开表
        KuduTable table = client.openTable("person");
        //3、创建一个session会话
        KuduSession session = client.newSession();
        //4、创建插入
        Insert insert = table.newInsert();
        //5、指定插入数据
        insert.getRow().addInt("id",1);
        insert.getRow().addInt("age",18);
        insert.getRow().addString("name","张三");
        //6、应用插入
        session.apply(insert);

        session.close();

        client.close();

    }
    /**
     * 更新数据
     * @throws Exception
     */
    public static void update() throws Exception{
        //1、创建kudu client
        KuduClient client = new KuduClientBuilder(KUDU_MASTER).build();
        //2、打开表
        KuduTable table = client.openTable("person");

        KuduSession session = client.newSession();

        Update update = table.newUpdate();
        update.getRow().addInt("id",1);
        update.getRow().addString("name","李四");

        session.apply(update);
        session.flush();
        session.close();

        client.close();
    }
    /**
     * 删除数据
     * @throws Exception
     */
    public static void delete() throws Exception{
        //1、创建kudu client
        KuduClient client = new KuduClientBuilder(KUDU_MASTER).build();
        //2、打开表
        KuduTable table = client.openTable("person");

        KuduSession session = client.newSession();

        Delete delete = table.newDelete();
        delete.getRow().addInt("id",1);


        session.apply(delete);
        session.flush();
        session.close();

        client.close();
    }

    /**
     * 条件查询 select * from person where id=1
     * @throws Exception
     */
    public static void query() throws Exception{
        //1、创建kudu client
        KuduClient client = new KuduClientBuilder(KUDU_MASTER).build();
        //2、打开表
        KuduTable table = client.openTable("person");
        //3、创建scanner扫描器
        KuduScanner.KuduScannerBuilder kuduScannerBuilder = client.newScannerBuilder(table);
        //4、创建查询条件
        KuduPredicate filter = KuduPredicate.newComparisonPredicate(table.getSchema().getColumn("id"), KuduPredicate.ComparisonOp.EQUAL, 1);
        //5、将查询条件加入到scanner中
        KuduScanner scanner = kuduScannerBuilder.addPredicate(filter).build();
        //6、获取查询结果
        while (scanner.hasMoreRows()){
            RowResultIterator rows = scanner.nextRows();
            while (rows.hasNext()){
                RowResult row = rows.next();
                Integer id = row.getInt("id");
                String name = row.getString("name");
                int age = row.getInt("age");
                System.out.println(id+"---"+name+"---"+age);
            }
        }
        //7、关闭client
        client.close();
    }

}
