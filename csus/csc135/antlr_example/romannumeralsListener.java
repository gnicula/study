// Generated from romannumerals.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link romannumeralsParser}.
 */
public interface romannumeralsListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link romannumeralsParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(romannumeralsParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link romannumeralsParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(romannumeralsParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link romannumeralsParser#thousands}.
	 * @param ctx the parse tree
	 */
	void enterThousands(romannumeralsParser.ThousandsContext ctx);
	/**
	 * Exit a parse tree produced by {@link romannumeralsParser#thousands}.
	 * @param ctx the parse tree
	 */
	void exitThousands(romannumeralsParser.ThousandsContext ctx);
	/**
	 * Enter a parse tree produced by {@link romannumeralsParser#thous_part}.
	 * @param ctx the parse tree
	 */
	void enterThous_part(romannumeralsParser.Thous_partContext ctx);
	/**
	 * Exit a parse tree produced by {@link romannumeralsParser#thous_part}.
	 * @param ctx the parse tree
	 */
	void exitThous_part(romannumeralsParser.Thous_partContext ctx);
	/**
	 * Enter a parse tree produced by {@link romannumeralsParser#hundreds}.
	 * @param ctx the parse tree
	 */
	void enterHundreds(romannumeralsParser.HundredsContext ctx);
	/**
	 * Exit a parse tree produced by {@link romannumeralsParser#hundreds}.
	 * @param ctx the parse tree
	 */
	void exitHundreds(romannumeralsParser.HundredsContext ctx);
	/**
	 * Enter a parse tree produced by {@link romannumeralsParser#hun_part}.
	 * @param ctx the parse tree
	 */
	void enterHun_part(romannumeralsParser.Hun_partContext ctx);
	/**
	 * Exit a parse tree produced by {@link romannumeralsParser#hun_part}.
	 * @param ctx the parse tree
	 */
	void exitHun_part(romannumeralsParser.Hun_partContext ctx);
	/**
	 * Enter a parse tree produced by {@link romannumeralsParser#hun_rep}.
	 * @param ctx the parse tree
	 */
	void enterHun_rep(romannumeralsParser.Hun_repContext ctx);
	/**
	 * Exit a parse tree produced by {@link romannumeralsParser#hun_rep}.
	 * @param ctx the parse tree
	 */
	void exitHun_rep(romannumeralsParser.Hun_repContext ctx);
	/**
	 * Enter a parse tree produced by {@link romannumeralsParser#tens}.
	 * @param ctx the parse tree
	 */
	void enterTens(romannumeralsParser.TensContext ctx);
	/**
	 * Exit a parse tree produced by {@link romannumeralsParser#tens}.
	 * @param ctx the parse tree
	 */
	void exitTens(romannumeralsParser.TensContext ctx);
	/**
	 * Enter a parse tree produced by {@link romannumeralsParser#tens_part}.
	 * @param ctx the parse tree
	 */
	void enterTens_part(romannumeralsParser.Tens_partContext ctx);
	/**
	 * Exit a parse tree produced by {@link romannumeralsParser#tens_part}.
	 * @param ctx the parse tree
	 */
	void exitTens_part(romannumeralsParser.Tens_partContext ctx);
	/**
	 * Enter a parse tree produced by {@link romannumeralsParser#tens_rep}.
	 * @param ctx the parse tree
	 */
	void enterTens_rep(romannumeralsParser.Tens_repContext ctx);
	/**
	 * Exit a parse tree produced by {@link romannumeralsParser#tens_rep}.
	 * @param ctx the parse tree
	 */
	void exitTens_rep(romannumeralsParser.Tens_repContext ctx);
	/**
	 * Enter a parse tree produced by {@link romannumeralsParser#ones}.
	 * @param ctx the parse tree
	 */
	void enterOnes(romannumeralsParser.OnesContext ctx);
	/**
	 * Exit a parse tree produced by {@link romannumeralsParser#ones}.
	 * @param ctx the parse tree
	 */
	void exitOnes(romannumeralsParser.OnesContext ctx);
	/**
	 * Enter a parse tree produced by {@link romannumeralsParser#ones_rep}.
	 * @param ctx the parse tree
	 */
	void enterOnes_rep(romannumeralsParser.Ones_repContext ctx);
	/**
	 * Exit a parse tree produced by {@link romannumeralsParser#ones_rep}.
	 * @param ctx the parse tree
	 */
	void exitOnes_rep(romannumeralsParser.Ones_repContext ctx);
}