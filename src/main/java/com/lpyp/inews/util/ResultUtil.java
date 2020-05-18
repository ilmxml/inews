package com.lpyp.inews.util;

public class ResultUtil {
    public static ResultRes success(Object data){
        ResultRes resultRes=new ResultRes();
        resultRes.setCode(200);
        resultRes.setRes("query success!");
        resultRes.setResult(data);
        return  resultRes;
    }
    public static ResultRes success(){
        ResultRes resultRes=new ResultRes();
        resultRes.setCode(200);
        resultRes.setRes("query success!");
        return  resultRes;
    }
    public static ResultRes serverError(){
        ResultRes resultRes=new ResultRes();
        resultRes.setCode(500);
        resultRes.setRes("server error!");
        return  resultRes;
    }
    public static ResultRes queryError(){
        ResultRes resultRes=new ResultRes();
        resultRes.setCode(500);
        resultRes.setRes("query error!");
        return  resultRes;
    }
    public static ResultRes customStatus(int code,String res,Object data){
        ResultRes resultRes=new ResultRes();
        resultRes.setCode(code);
        resultRes.setRes(res);
        if (data!=null)
            resultRes.setResult(data);
        return  resultRes;
    }
}
