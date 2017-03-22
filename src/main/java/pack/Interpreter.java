package pack;

/**
 * Created by christian on 3/18/17.
 */
public class Interpreter {
    private final Parser parser;

    public Interpreter(Parser parser) {
        this.parser = parser;
    }

    public Integer interpret() {
        return visit(parser.parse());
    }

    public Integer visitBinOp(BinOp node) {
        Integer left = this.visit(node.getLeft());
        Integer right = this.visit(node.getRight());
        if (node.getToken().getTokenType() == TokenType.AND) {
            if(left == null || right == null){
                return this.visit(new Num(new Token(null, null), null));
            }
            return this.visit(node.getLeft()) + this.visit(node.getRight());
        } else if (node.getToken().getTokenType() == TokenType.OR) {
            if (left == null || right == null){
                return left == null ? right : left;
            }
            return Math.min(this.visit(node.getLeft()), this.visit(node.getRight()));
        }

        throw new RuntimeException("Invalid Node");

    }

    public Integer visit(Ast node) {
        if (node instanceof BinOp) {
            return this.visitBinOp((BinOp) node);
        } else {
            return this.visitNum(node);
        }
    }

    private Integer visitNum(Ast node) {
        Num num = (Num) node;
        return num.getValue();
    }
}
