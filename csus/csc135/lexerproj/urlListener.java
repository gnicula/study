// Generated from url.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link urlParser}.
 */
public interface urlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link urlParser#url}.
	 * @param ctx the parse tree
	 */
	void enterUrl(urlParser.UrlContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#url}.
	 * @param ctx the parse tree
	 */
	void exitUrl(urlParser.UrlContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#uri}.
	 * @param ctx the parse tree
	 */
	void enterUri(urlParser.UriContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#uri}.
	 * @param ctx the parse tree
	 */
	void exitUri(urlParser.UriContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#scheme}.
	 * @param ctx the parse tree
	 */
	void enterScheme(urlParser.SchemeContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#scheme}.
	 * @param ctx the parse tree
	 */
	void exitScheme(urlParser.SchemeContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#host}.
	 * @param ctx the parse tree
	 */
	void enterHost(urlParser.HostContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#host}.
	 * @param ctx the parse tree
	 */
	void exitHost(urlParser.HostContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DomainNameOrIPv4Host}
	 * labeled alternative in {@link urlParser#hostname}.
	 * @param ctx the parse tree
	 */
	void enterDomainNameOrIPv4Host(urlParser.DomainNameOrIPv4HostContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DomainNameOrIPv4Host}
	 * labeled alternative in {@link urlParser#hostname}.
	 * @param ctx the parse tree
	 */
	void exitDomainNameOrIPv4Host(urlParser.DomainNameOrIPv4HostContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IPv6Host}
	 * labeled alternative in {@link urlParser#hostname}.
	 * @param ctx the parse tree
	 */
	void enterIPv6Host(urlParser.IPv6HostContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IPv6Host}
	 * labeled alternative in {@link urlParser#hostname}.
	 * @param ctx the parse tree
	 */
	void exitIPv6Host(urlParser.IPv6HostContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#v6host}.
	 * @param ctx the parse tree
	 */
	void enterV6host(urlParser.V6hostContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#v6host}.
	 * @param ctx the parse tree
	 */
	void exitV6host(urlParser.V6hostContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#port}.
	 * @param ctx the parse tree
	 */
	void enterPort(urlParser.PortContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#port}.
	 * @param ctx the parse tree
	 */
	void exitPort(urlParser.PortContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#path}.
	 * @param ctx the parse tree
	 */
	void enterPath(urlParser.PathContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#path}.
	 * @param ctx the parse tree
	 */
	void exitPath(urlParser.PathContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#user}.
	 * @param ctx the parse tree
	 */
	void enterUser(urlParser.UserContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#user}.
	 * @param ctx the parse tree
	 */
	void exitUser(urlParser.UserContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#login}.
	 * @param ctx the parse tree
	 */
	void enterLogin(urlParser.LoginContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#login}.
	 * @param ctx the parse tree
	 */
	void exitLogin(urlParser.LoginContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#password}.
	 * @param ctx the parse tree
	 */
	void enterPassword(urlParser.PasswordContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#password}.
	 * @param ctx the parse tree
	 */
	void exitPassword(urlParser.PasswordContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#frag}.
	 * @param ctx the parse tree
	 */
	void enterFrag(urlParser.FragContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#frag}.
	 * @param ctx the parse tree
	 */
	void exitFrag(urlParser.FragContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(urlParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(urlParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#search}.
	 * @param ctx the parse tree
	 */
	void enterSearch(urlParser.SearchContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#search}.
	 * @param ctx the parse tree
	 */
	void exitSearch(urlParser.SearchContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#searchparameter}.
	 * @param ctx the parse tree
	 */
	void enterSearchparameter(urlParser.SearchparameterContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#searchparameter}.
	 * @param ctx the parse tree
	 */
	void exitSearchparameter(urlParser.SearchparameterContext ctx);
	/**
	 * Enter a parse tree produced by {@link urlParser#string}.
	 * @param ctx the parse tree
	 */
	void enterString(urlParser.StringContext ctx);
	/**
	 * Exit a parse tree produced by {@link urlParser#string}.
	 * @param ctx the parse tree
	 */
	void exitString(urlParser.StringContext ctx);
}