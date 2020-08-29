package com.enjoygolf24.api.common.utility.pdf;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

@Component
public class PdfPagingAssistent {

    /** 総ページ数*/
    int totalPage;

    /** データ総ページ数*/
    int dataTotalPage;

    /** ページごとのデータ数*/
    int pagePerCount;

    /** データリスト*/
    List<Object> contentsList;

    /** デフォルトマップ*/
    Map<String, Object> defaultMap;

    /** ページごとのデータリストマップ*/
    Map<Integer, List<Object>> pagingListMap = new HashMap<Integer, List<Object>>();

    List<Integer> startPageList;


    /**
     * データを設定する。
     * @param pPagePerCount ページごとの表示データ数
     * @param pageHeaderCount ヘッダ代わりに入るデータ数
     * @param pageFooterCount フッター代わりに入るデータ数
     * @param param パラメータマップ
     * @param data データリスト
     */
    public void init(int pPagePerCount, int pageHeaderCount, int pageFooterCount, Map<String, Object> param, List<?> data) {

        defaultMap = param;
        startPageList = new ArrayList<Integer>();

        //1page
        int totalCount = data.size();
        pagePerCount = pPagePerCount + pageFooterCount;
        if (totalCount > pagePerCount) {
            totalPage = 1;
            int remainDataToal = totalCount - pagePerCount;
            pagePerCount += pageHeaderCount;
            totalPage += (remainDataToal / pagePerCount);
            dataTotalPage = totalPage;

            int lastSize = remainDataToal % pagePerCount;

            if (lastSize != 0) {
                totalPage += 1;
                dataTotalPage += 1;

                if (lastSize > (pPagePerCount + pageHeaderCount)) {
                    totalPage += 1;
                }
            } else {
                totalPage += 1;
            }


        } else {
            totalPage = 1;
            dataTotalPage = 1;
            if (totalCount > pPagePerCount) {
                totalPage += 1;
            }
        }

        if (pageFooterCount == 0) {
            totalPage = dataTotalPage;
        }

        int pageNo = 1;
        int index = 1;
        int loopCount = 1;
        pagingListMap.put(pageNo, new ArrayList<Object>());
        startPageList.add(1);
        int pagePerCountWithPage = pPagePerCount + pageFooterCount;

        for (Object obj : data) {
            pagingListMap.get(pageNo).add(obj);
            if (loopCount % pagePerCountWithPage == 0 && pageNo != totalPage) {
                pageNo++;
                if (pageNo == 2) {
                    pagePerCountWithPage += pageHeaderCount;
                    loopCount = 0;
                }
                startPageList.add(index + 1);

                pagingListMap.put(pageNo, new ArrayList<Object>());

            }
            index++;
            loopCount++;
        }
    }

    /**
     *コンテキストを取得する。
     * @param pageNo ページ番号
     * @return コンテキスト
     */
    public Context getContext(int pageNo) {

        Context context = new Context();
        if (defaultMap != null) {
            Iterator<?> itMap = defaultMap.entrySet().iterator();
            while (itMap.hasNext()) {
                @SuppressWarnings("rawtypes")
                Map.Entry pair = (Map.Entry)itMap.next();
                context.setVariable(pair.getKey().toString(), pair.getValue());
            }
        }
        context.setVariable("pageNo", pageNo);
        context.setVariable("totalPage", totalPage);
        context.setVariable("dataTotalPage", dataTotalPage);
        context.setVariable("dataList", pagingListMap.get(pageNo));
        if (startPageList.size() >= pageNo) {
            context.setVariable("startIndex", startPageList.get(pageNo - 1));
        } else {
            context.setVariable("startIndex", "1");
        }
        return context;
    }

    /**
     * 複数ページかチェックする。
     * @return true:複数ページ
     */
    public boolean isMultiPages() {
        return totalPage > 1;
    }

    /**
     * 総ページ数を取得する。
     * @return 総ページ数
     */
    public int getTotalPage() {
        return this.totalPage;
    }

}
