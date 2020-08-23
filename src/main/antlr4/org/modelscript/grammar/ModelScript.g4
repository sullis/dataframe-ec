grammar ModelScript;

script :
      functionDeclarationExpr*
      statementSequence
      projectionStatement?
    ;

functionDeclarationExpr :
    'function' ID ('(' idList ')' | '(' ')')?
    '{'
        statementSequence
    '}'
    ;

statementSequence: (statement)*;

statement :
      ID '=' expr     #assignExpr
    | IF expr
      THEN ifBody=statementSequence
      (ELSE elseBody=statementSequence)?
      ENDIF           #conditionExpr
    | expr            #freeExp
    ;

projectionStatement :
    'project'? '{' exprList '}' ('where' expr)?
    ;

expr :
      ID ( '.' ID)+?           #propertyPathExpr
    | ID  ':' expr             #aliasExpr
    | ID ('(' exprList ')' | '(' ')') #functionCallExpr
    | '(' expr ')'             #parenExpr
    | '-' expr                 #unaryMinusExpr
    | '!' expr                 #notExpr
    | expr op=(MUL | DIV) expr #mulDivExpr
    | expr op=(ADD | SUB) expr #addSubExpr
    | expr op=(GT | GTE | LT | LTE | EQ | NE) expr #compareExpr
    | expr op=AND expr         #andExpr
    | expr op=(OR | XOR) expr  #orExpr
    | expr IN vectorExpr       #inExpr
    | vectorExpr               #standaloneVectorExpr
    | INT                      #intLiteralExpr
    | DOUBLE                   #doubleLiteralExpr
    | STRING                   #stringLiteralExpr
    | ID                       #varExpr
    ;

vectorExpr : '[' exprList ']' | '[' ']';

exprList :  expr ( ',' expr )*;

idList : ID ( ',' ID) *;

MUL : '*' ;
DIV : '/' ;
ADD : '+' ;
SUB : '-' ;

GT  : '>' ;
GTE : '>=' ;
LT  : '<' ;
LTE : '<=' ;
EQ  : '==';
NE  : '!=';

IN  : 'in' | 'IN' ;

AND  : 'and' | 'AND';
OR   : 'or' | 'OR';
XOR  : 'xor' | 'XOR';

IF    : 'if' | 'IF';
THEN  : 'then' | 'THEN';
ELSE  : 'else' | 'ELSE';
ENDIF : 'endif' | 'ENDIF';

ID : LETTER (LETTER|DIGIT)*;
INT : DIGIT+;
DOUBLE : ('.' DIGIT+ | DIGIT+ ('.' DIGIT*)? ) ;
WS : [ \t]+ -> skip;
NL : [\n\r] -> skip;
//NL : [\n\r];
STRING : '"' ( ESC | . )*? '"' ;

fragment
ESC : '\\' [btnr"\\];
LETTER : [a-zA-Z\u0080-\u00FF_];
DIGIT  : [0-9];