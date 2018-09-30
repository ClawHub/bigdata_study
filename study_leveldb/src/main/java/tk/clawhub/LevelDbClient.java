/**************************************************************************************** 
 南京小视科技有限公司           
 ****************************************************************************************/
package tk.clawhub;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.iq80.leveldb.DB;
import org.iq80.leveldb.DBFactory;
import org.iq80.leveldb.DBIterator;
import org.iq80.leveldb.Options;
import org.iq80.leveldb.ReadOptions;
import org.iq80.leveldb.Snapshot;
import org.iq80.leveldb.WriteOptions;
import org.iq80.leveldb.impl.Iq80DBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * The type Level db client.
 */
@Component
public class LevelDbClient {
    /**
     * The Logger.
     */
    private Logger logger = LogManager.getLogger(LevelDbClient.class);

    /**
     * 是否清除数据.
     */
    @Value("${level.db.cleanup}")
    private boolean cleanup;
    /**
     * The Path.
     */
    @Value("${level.db.path}")
    private String path;
    /**
     * utf-8
     */
    private Charset charset = Charset.forName("utf-8");
    /**
     * The constant db.
     */
    private DB db;
    /**
     * 数据计数器.
     */
    private int count;

    /**
     * Description: 获取计数器 <br>
     *
     * @return int
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public int getCount() {
        return count;
    }

    /**
     * Description: Get charset <br>
     *
     * @return charset
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * Description: Init <br>
     *
     * @author LiZhiming <br>
     * @taskId <br>
     */
    @PostConstruct
    public void init() {
        logger.info("=============LevelDbClient 初始化=============");
        //DB工厂
        DBFactory factory = Iq80DBFactory.factory;
        File dir = new File(path);
        //如果数据不需要reload，则每次重启，尝试清理磁盘中path下的旧数据。
        if (cleanup) {
            logger.info("LevelDbClient 清除:{}文件夹内的所有文件", path);
            try {
                //清除文件夹内的所有文件。
                factory.destroy(dir, null);
            } catch (IOException e) {
                logger.error("LevelDbClient 清理磁盘中path下的旧数据发生异常：{}", e.getMessage());
            }
        }
        Options options = new Options().createIfMissing(true);
        try {
            logger.info("LevelDbClient重新open新的db");
            //重新open新的db
            db = factory.open(dir, options);
        } catch (IOException e) {
            logger.error("LevelDbClient 重新open新的db发生异常：{}", e.getMessage());
        }
        logger.info("=============LevelDbClient 初始化 结束=============");
    }

    /**
     * Description: 删除Key <br>
     *
     * @param key key
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void delete(String key) {
        db.delete(key.getBytes(charset));
        count--;
    }

    /**
     * Description: 根据Key获取Val <br>
     *
     * @param key key
     * @return string
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public String read(String key) {
        byte[] bv = db.get(key.getBytes(charset));
        if (bv != null && bv.length > 0) {
            return new String(bv, charset);
        }
        return "";
    }

    /**
     * Description: 写入Key与Val <br>
     *
     * @param key key
     * @param val val
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void write(String key, String val) {
        db.put(key.getBytes(charset), val.getBytes(charset));
        count++;
    }

    /**
     * Description: write后立即进行磁盘同步写 <br>
     *
     * @param key key
     * @param val val
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void writeSync(String key, String val) {
        //write后立即进行磁盘同步写,线程安全
        WriteOptions writeOptions = new WriteOptions().sync(true);
        db.put(key.getBytes(charset), val.getBytes(charset), writeOptions);
        count++;
    }

    /**
     * Description: 获取 iterator <br>
     *
     * @return db iterator
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public DBIterator getIterrator() {
        //iterator，遍历，顺序读
        //读取当前snapshot，快照，读取期间数据的变更，不会反应出来
        Snapshot snapshot = db.getSnapshot();
        //读选项
        ReadOptions readOptions = new ReadOptions();
        //遍历中swap出来的数据，不应该保存在memtable中。
        readOptions.fillCache(false);
        //默认snapshot为当前。
        readOptions.snapshot(snapshot);
        return db.iterator(readOptions);
    }

    /**
     * Description: Close iterator <br>
     *
     * @param iterator iterator
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void closeIterator(DBIterator iterator) {
        try {
            iterator.close();
        } catch (IOException e) {
            logger.error("关闭iterator发生异常：{}", e.getMessage());
        }
    }

    /**
     * Description: Close db <br>
     *
     * @throws IOException io exception
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void closeDB() {
        try {
            db.close();
        } catch (IOException e) {
            logger.error("关闭db发生异常：{}", e.getMessage());
        }
    }

    /**
     * Description: 清理磁盘中path下的旧数据 <br>
     *
     * @author LiZhiming <br>
     * @taskId <br>
     */
    public void clearPathFile() {
        //DB工厂
        DBFactory factory = Iq80DBFactory.factory;
        File dir = new File(path);
        //清理磁盘中path下的旧数据。
        logger.info("LevelDbClient 清除:{}文件夹内的所有文件", path);
        try {
            //清除文件夹内的所有文件。
            factory.destroy(dir, null);
        } catch (IOException e) {
            logger.error("LevelDbClient 清理磁盘中path下的旧数据发生异常：{}", e.getMessage());
        }
    }
}
