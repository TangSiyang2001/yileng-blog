package com.tsy.blog.web.vo.params;

import lombok.Data;

/**
 * @author Steven.T
 * @date 2021/10/8
 * vo：与前端交互数据
 * 分页信息
 */
@Data
public class PageParam {
    /**
     * 默认页码为1
     */
    private int page=1;
    /**
     * 默认页面容量为10条
     */
    private int pageSize=10;
    /**
     * 需要的文章的分类id
     */
    private Long categoryId;
    /**
     * 需要的文章标签的id
     */
    private Long tagId;
    /**
     * 年份
     */
    private String year;
    /**
     * 月份
     */
    private String month;

    /**
     * 如果月份是一位数，就加0
     * @return 月份
     */
    public String getMonth(){
        if(this.month!=null && this.month.length()==1){
            return "0"+this.month;
        }
        return this.month;
    }
}
