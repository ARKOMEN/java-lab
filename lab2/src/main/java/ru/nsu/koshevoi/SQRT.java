package ru.nsu.koshevoi;

public class SQRT implements Command{
    @Override
    public void command(Data data, String[] strings) throws EmptyStack, ExtractingRootFromNegativeNumber {
        if(data.stack.isEmpty()){
            throw new EmptyStack();
        }
        else{
            double tmp = data.stack.pop();
            if(tmp < 0){
                data.stack.push(tmp);
                throw new ExtractingRootFromNegativeNumber();
            }
            else {
                data.stack.push(Math.sqrt(tmp));
            }
        }
    }
}
