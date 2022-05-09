package com.webstarter.manage.model;

/**
 *
 * @param <T>  돌려받을 객체 타입 입력
 *           1. Error Msg에 값이 입력되면 isSuccess False
 *           2. resObjectData가 null 일경우 isSuccess  False
 *           3. T Generic에 받을 값이 없을경우 <String> 설정 및 resObject에 "" 입력
 */

public class ResponseService<T> {
    private boolean isSuccess = true;
    private T resObjectData = null;
    private String errorMsg = "";  // 에러 메시지가 있으면 isSuccess false

    public ResponseService(String errorMsg, T resObjectData){
        isSuccess = true;
        this.resObjectData = resObjectData;
        if(errorMsg == null){
            this.errorMsg = "";
        }else{
            this.errorMsg = errorMsg;
        }
    }

    public boolean isSuccess() {
        checkSuccess();
        return isSuccess;
    }

    public T getResObjectData() {
        return resObjectData;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    private void checkSuccess(){
        if(this.errorMsg != null){
            if(this.errorMsg.length() > 0){
                // 메시지가 있으면 실패
                this.isSuccess = false;
            }
            if(resObjectData==null){
                // 값이 없어도 실패
                this.isSuccess = false;
            }
        }else{
            // errorMsg에 null이 리턴되지 않도록
            this.errorMsg = "";
        }

    }
}
