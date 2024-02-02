package ru.nsu.koshevoi;
import javax.naming.NamingEnumeration;
import java.io.*;
import java.lang.StringBuilder;
import java.util.*;
import java.util.stream.Collectors;

class Word{
    static int all = 0;
    public int cnt;
    StringBuilder word;

    public Word(StringBuilder w){
        word = w;
        cnt++;
    }
    public StringBuilder getWord(){return word;}

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
}

public class Main{
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.err.println("no file or directory.");
            return;
        }

        HashSet<Word> set = new HashSet<Word>();
        int symb;
        StringBuilder str = new StringBuilder();
        try(BufferedReader buff = new BufferedReader(new FileReader(args[0]))){
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
        Set<Word> sortSet = set.stream().sorted(Comparator.comparingInt(Word::getCnt)).collect(Collectors.toSet());
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("file.csv"))){
            Iterator<Word> itr = sortSet.iterator();
            while(itr.hasNext()){
                Word next = itr.next();
                writer.write(next.getWord().toString() + ',' + next.cnt + ',' + next.getCnt() * 100 / Word.all + "%\n");
            }
        }
    }
}