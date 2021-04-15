import java.util.ArrayList;

public class Main {
    public static void main(String[] args){

        InputFileReader fileReader = new InputFileReader();
        ArrayList<Item> inputItems = fileReader.readActions();
        for(Item item : inputItems)
            item.setResult();
        new OutputFileWriter().writeResult(fileReader.getFilename(), inputItems);
    }
}
