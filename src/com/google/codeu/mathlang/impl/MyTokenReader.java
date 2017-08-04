// Copyright 2017 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.codeu.mathlang.impl;

import java.io.IOException;
import java.lang.StringBuilder;
import java.lang.Character;
import java.lang.Double;

import com.google.codeu.mathlang.core.tokens.Token;
import com.google.codeu.mathlang.core.tokens.NameToken;
import com.google.codeu.mathlang.core.tokens.NumberToken;
import com.google.codeu.mathlang.core.tokens.StringToken;
import com.google.codeu.mathlang.core.tokens.SymbolToken;
import com.google.codeu.mathlang.parsing.TokenReader;

// MY TOKEN READER
//
// This is YOUR implementation of the token reader interface. To know how
// it should work, read src/com/google/codeu/mathlang/parsing/TokenReader.java.
// You should not need to change any other files to get your token reader to
// work with the test of the system.
public final class MyTokenReader implements TokenReader {

  private String source;
  private int index;

  public MyTokenReader(String source) {
    // Your token reader will only be given a string for input. The string will
    // contain the whole source (0 or more lines).
   this.source = source;
   index = 0;
  }

  @Override
  public Token next() throws IOException {
    // Most of your work will take place here. For every call to |next| you should
    // return a token until you reach the end. When there are no more tokens, you
    // should return |null| to signal the end of input.

    // If for any reason you detect an error in the input, you may throw an IOException
    // which will stop all execution.

    if (index >= source.length())
      return null;

    // Scan the next input value
    char character = source.charAt(index);
    index++;
    //System.out.println("**" + character + "**");

    // If there is whitespace, read until it is gone
    while (character == ' ' || character == '\n') {
      character = source.charAt(index);
      index++;
    }

    // If the character is a symbol, return the SymbolToken
    if (character == '=' || character == '+' || character == '-' || character == ';')
      return new SymbolToken(character);

    // If the character is a number, return the NumberToken of it
    if (Character.isDigit(character))
      return new NumberToken(Double.parseDouble("" + character));

    // If the beginning of the string is a ", then we want to read
    // until the next "
    if (character == '"') {

      StringBuilder stringbuilder = new StringBuilder();
      char next = source.charAt(index);
      index++;

      // Keep reading in until we find a "
      while (next != '"') {
        stringbuilder.append(next);
        next = source.charAt(index);
        index++;
      }

      // Return the token
      StringToken token = new StringToken(stringbuilder.toString());
      //System.out.println(token);
      return token;
    }

    // build the word liek normal if not "
    StringBuilder stringbuilder = new StringBuilder();
    stringbuilder.append(character);
    char next = source.charAt(index);
    index++;

    // Keep reading in until we find a ' ' or symbol
    while (!(next == ' ' || next == ';' || next == '=' || next == '+' || next == '-')) {
      stringbuilder.append(next);
      next = source.charAt(index);
      index++;
    }

    // If we read in a symbol we want to decrement to re-read it
    if (next != ' ')
      index--;

    NameToken token = new NameToken(stringbuilder.toString());
    //System.out.println(token);
    return token;
  }
}
