package cn.common.util.algorithm;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页|按份数平均分割|按指定大小分割
 */
public class ListUtil {
    /**
     * @param list     进行分页的list
     * @param currPage 当前页
     * @param pageNum  每页显示条数
     * @return 分页后数据
     */
    public static <T> List<T> pageList(List<T> list, Integer currPage, Integer pageNum) {
        Integer size=list.size();
        List<T> pageList=null;
        if (list == null) {
            list = new ArrayList<T>();
        }
        if (currPage == null) {
            currPage = 1;
        }
        if (pageNum == null) {
            pageNum = 10;
        }
        Integer start = (currPage - 1) * pageNum;
        Integer end=currPage*pageNum;
        if(start>=size){//起始大于等于总条数 返回空
            pageList=new ArrayList<>();
        } else  {
            if(end<=size){
                pageList = list.subList(start, end);
            }else {
                pageList=list.subList(start,size);
            }
        }
        return pageList;
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = Lists.newArrayList();
        int remaider = source.size() % n;  //(先计算出余数)
        int number = source.size() / n;  //然后是商
        int offset = 0;//偏移量
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remaider > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remaider--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     *
     * 将数组按照固定大小分成二维数组
     *
     * @param target
     * @param size
     * @return
     */
    public static<T>  List<List<T>>  AssignBatchList(List<T> target,int size) {
        List<List<T>> listArr = Lists.newArrayList();
        int count=target.size();
        //获取被拆分的数组个数
        int arrSize = count%size==0?count/size:count/size+1;
        for(int i=0;i<arrSize;i++) {
            List<T>  sub = new ArrayList<T>();
            //把指定索引数据放入到list中
            for(int j=i*size;j<=size*(i+1)-1;j++) {
                if(j<=count-1) {
                    sub.add(target.get(j));
                }
            }
            listArr.add(sub);
        }
        return listArr;
    }

    public static void main(String[] args) {
        List<String> s=new ArrayList<>();
        s.add("1");
        s.add("2");
        s.add("3");
        s.add("4");
        s.add("5");
        s.add("6");
        s.add("7");
        s.add("8");
       System.out.println( ListUtil.pageList(s,3,4));
    }

}
