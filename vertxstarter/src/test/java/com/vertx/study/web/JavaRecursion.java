package com.vertx.study.web;

public class JavaRecursion {
  public static void main(String[] args) {
    int N =3;
    solve(N);
  }

  public static void solve(int N){
    if(N==0)
      return;
    solve(N-1);
    System.out.println(N);
  }
}
