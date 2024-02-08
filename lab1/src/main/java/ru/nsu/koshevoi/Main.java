package ru.nsu.koshevoi;
import java.io.*;
import java.lang.StringBuilder;
import java.util.*;

class Word{
    static int all = 0;
    private int cnt = 0;
    private final StringBuilder word;

    public Word(StringBuilder w){
        word = w;
        cnt++;
    }
    public int getCnt(){return cnt;}
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word other = (Word) o;
        return word.compareTo(other.word) == 0;
    }

    @Override
    public int hashCode(){
        return word.hashCode();
    }

    static List<Word> parser(String fileName) throws IOException{
        HashSet<Word> set = new HashSet<Word>();
        int symb;
        StringBuilder str = new StringBuilder();
        try(BufferedReader buff = new BufferedReader(new FileReader(fileName))){
            while((symb = buff.read()) != -1){
                if(Character.isLetterOrDigit(symb)){
                    str.append(Character.toChars(symb));
                }
                else if(!str.isEmpty()){
                    Word.all++;
                    Iterator<Word> itr = set.iterator();
                    if(itr.hasNext()){
                        Word next = itr.next();
                        Word strBuff = new Word(str);
                        while(!next.equals(strBuff) && itr.hasNext()){
                            next = itr.next();
                        }
                        if(next.equals(strBuff)){
                            next.cnt++;
                        }
                        else{
                            set.add(new Word(str));
                        }
                    }
                    else{
                        set.add(new Word(str));
                    }
                    str = new StringBuilder();
                }
            }
        }
        List<Word> list = new ArrayList<>(set);
        list.sort(Comparator.comparingInt(Word::getCnt).reversed());
        return list;
    }

    static void createCSV(List<Word> list) throws IOException{
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("file.csv"))){
            Iterator<Word> itr = list.iterator();
            while(itr.hasNext()){
                Word next = itr.next();
                writer.write(next.word.toString() + ',' + next.cnt + ',' + next.getCnt() * 100 / Word.all + "%" + "\n");
            }
        }
    }
}

public class Main{
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.err.println("error, enter the file name");
            return;
        }

        List<Word> list = Word.parser(args[0]);
        Word.createCSV(list);
    }
}