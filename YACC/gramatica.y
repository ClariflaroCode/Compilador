    %{
        import java.io.*;
    %}

    %token NUM
    %token PLUS MINUS TIMES DIVIDE
    %token LPAREN RPAREN

    %%

    expr
        : expr PLUS term
            { $$ = $1 + $3}
        | expr MINUS term
            { $$ = $1 - $3}
        | term
            { $$ = $1}
        ;

    term
        : term DIVIDES factor
            { $$ = $1 / $3}
        | term TIMES factor
            { $$ = $1 * $3}
        | factor
            { $$ = $1}
        ;

    factor
        : NUM
            { $$ = $1}
        | LPAREN expr RPAREN
            { $$ = $2}
        ;

    %%
