package org.project.bestpractice.exceptions;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMesage {

    private MessageTypes messageType;

    private String offStatic;

    public String buildErrorMessage(){
        StringBuilder message = new StringBuilder();
        message.append(this.getMessageType());
        if(this.getOffStatic()!=null){
            message.append(" : ").append(this.offStatic);
        }
        return message.toString();
    }

}
