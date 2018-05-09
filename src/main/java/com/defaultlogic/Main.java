package com.defaultlogic;

import com.defaultlogic.check.ModelCheckResult;
import com.defaultlogic.check.ModelChecker;
import com.defaultlogic.model.DefaultLogicQuery;
import com.defaultlogic.parser.DefaultLogicFileParser;

import java.io.IOException;

public class Main {

  public static void main(String[] args) throws IOException {

    DefaultLogicFileParser fileParser = new DefaultLogicFileParser();
    DefaultLogicQuery logicQuery = fileParser.parseFile("lakas.txt");

    ModelChecker modelChecker = new ModelChecker();
    ModelCheckResult result = modelChecker.check(logicQuery);

    System.out.println("[" + result.getType().toString() + "] - " + result.getReason());
  }

}
