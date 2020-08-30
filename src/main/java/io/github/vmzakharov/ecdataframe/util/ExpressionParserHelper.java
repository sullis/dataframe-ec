package io.github.vmzakharov.ecdataframe.util;

import io.github.vmzakharov.ecdataframe.dsl.AnonymousScript;
import io.github.vmzakharov.ecdataframe.dsl.Expression;
import io.github.vmzakharov.ecdataframe.dsl.Script;
import io.github.vmzakharov.ecdataframe.grammar.ModelScriptLexer;
import io.github.vmzakharov.ecdataframe.grammar.ModelScriptParser;
import io.github.vmzakharov.ecdataframe.grammar.ModelScriptTreeBuilderVisitor;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class ExpressionParserHelper
{
    static public Expression toExpression(String s)
    {
        ModelScriptParser parser = stringToParser(s);
        ParseTree tree = parser.statement();

        ModelScriptTreeBuilderVisitor visitor = new ModelScriptTreeBuilderVisitor();

        return visitor.visit(tree);
    }

    static public Expression toProjection(String s)
    {
        ModelScriptParser parser = stringToParser(s);
        ParseTree tree = parser.projectionStatement();

        ModelScriptTreeBuilderVisitor visitor = new ModelScriptTreeBuilderVisitor();

        return visitor.visit(tree);
    }

    static public Expression toExpressionOrScript(String s)
    {
        AnonymousScript script = toScript(s);

        if (script.getExpressions().size() == 1 && script.getFunctions().size() == 0)
        {
            return script.getExpressions().get(0);
        }

        return script;
    }

    static public AnonymousScript toScript(String s)
    {
        ModelScriptParser parser = stringToParser(s);
        ParseTree tree = parser.script();

        ModelScriptTreeBuilderVisitor visitor = new ModelScriptTreeBuilderVisitor();
        visitor.visit(tree);

        return visitor.getAsAnonymousScript();
    }

    static private ModelScriptParser stringToParser(String s)
    {
        CharStream charStream = CharStreams.fromString(s);
        ModelScriptLexer lexer = new ModelScriptLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        return new ModelScriptParser(tokens);
    }
}
