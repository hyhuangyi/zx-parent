package cn.biz.webMagic.base;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 自定义Agent
 */
@Slf4j
public class Agents {
    private static final String AGENT_FILE_PATH = "User-Agents.txt";
    private static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static List<String> agents;

    public static String getRandom() {
        String random = getRandom(null);
       log.info("Agents======>" + random);
        return random;
    }

    private static String getRandom(String agent) {
        try {
            lock.readLock().lock();
            int size = agents.size();
            if (size == 0)
                return "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";
            Random random = new Random();
            if (null != agent) return agent;
            else return agents.get(random.nextInt(size));
        } catch (Exception e) {
            e.printStackTrace();
            return "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36";
        } finally {
            lock.readLock().unlock();
        }
    }

    static {
        agents = new ArrayList<>();
        InputStream resourceAsStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        try {
            resourceAsStream = Agents.class.getClassLoader().getResourceAsStream(AGENT_FILE_PATH);
            inputStreamReader = new InputStreamReader(resourceAsStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String len;
            while ((len = bufferedReader.readLine()) != null) {
                if (!len.matches("^#.*")) {
                    agents.add(len.trim());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != inputStreamReader) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != resourceAsStream) {
                try {
                    resourceAsStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


