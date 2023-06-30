// Generated from url.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class urlParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, DIGITS=13, HEX=14, STRING=15, WS=16;
	public static final int
		RULE_url = 0, RULE_uri = 1, RULE_scheme = 2, RULE_host = 3, RULE_hostname = 4, 
		RULE_v6host = 5, RULE_port = 6, RULE_path = 7, RULE_user = 8, RULE_login = 9, 
		RULE_password = 10, RULE_frag = 11, RULE_query = 12, RULE_search = 13, 
		RULE_searchparameter = 14, RULE_string = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"url", "uri", "scheme", "host", "hostname", "v6host", "port", "path", 
			"user", "login", "password", "frag", "query", "search", "searchparameter", 
			"string"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'://'", "':'", "'/'", "'.'", "'['", "']'", "'::'", "'@'", "'#'", 
			"'?'", "'&'", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "DIGITS", "HEX", "STRING", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "url.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public urlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class UrlContext extends ParserRuleContext {
		public UriContext uri() {
			return getRuleContext(UriContext.class,0);
		}
		public TerminalNode EOF() { return getToken(urlParser.EOF, 0); }
		public UrlContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_url; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterUrl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitUrl(this);
		}
	}

	public final UrlContext url() throws RecognitionException {
		UrlContext _localctx = new UrlContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_url);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			uri();
			setState(33);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UriContext extends ParserRuleContext {
		public SchemeContext scheme() {
			return getRuleContext(SchemeContext.class,0);
		}
		public HostContext host() {
			return getRuleContext(HostContext.class,0);
		}
		public LoginContext login() {
			return getRuleContext(LoginContext.class,0);
		}
		public PortContext port() {
			return getRuleContext(PortContext.class,0);
		}
		public QueryContext query() {
			return getRuleContext(QueryContext.class,0);
		}
		public FragContext frag() {
			return getRuleContext(FragContext.class,0);
		}
		public TerminalNode WS() { return getToken(urlParser.WS, 0); }
		public PathContext path() {
			return getRuleContext(PathContext.class,0);
		}
		public UriContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_uri; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterUri(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitUri(this);
		}
	}

	public final UriContext uri() throws RecognitionException {
		UriContext _localctx = new UriContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_uri);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			scheme();
			setState(36);
			match(T__0);
			setState(38);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				{
				setState(37);
				login();
				}
				break;
			}
			setState(40);
			host();
			setState(43);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(41);
				match(T__1);
				setState(42);
				port();
				}
			}

			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(45);
				match(T__2);
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==DIGITS || _la==STRING) {
					{
					setState(46);
					path();
					}
				}

				}
			}

			setState(52);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__9) {
				{
				setState(51);
				query();
				}
			}

			setState(55);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__8) {
				{
				setState(54);
				frag();
				}
			}

			setState(58);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WS) {
				{
				setState(57);
				match(WS);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SchemeContext extends ParserRuleContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public SchemeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scheme; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterScheme(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitScheme(this);
		}
	}

	public final SchemeContext scheme() throws RecognitionException {
		SchemeContext _localctx = new SchemeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_scheme);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(60);
			string();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HostContext extends ParserRuleContext {
		public HostnameContext hostname() {
			return getRuleContext(HostnameContext.class,0);
		}
		public HostContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_host; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterHost(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitHost(this);
		}
	}

	public final HostContext host() throws RecognitionException {
		HostContext _localctx = new HostContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_host);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(62);
				match(T__2);
				}
			}

			setState(65);
			hostname();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HostnameContext extends ParserRuleContext {
		public HostnameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hostname; }
	 
		public HostnameContext() { }
		public void copyFrom(HostnameContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IPv6HostContext extends HostnameContext {
		public V6hostContext v6host() {
			return getRuleContext(V6hostContext.class,0);
		}
		public IPv6HostContext(HostnameContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterIPv6Host(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitIPv6Host(this);
		}
	}
	public static class DomainNameOrIPv4HostContext extends HostnameContext {
		public List<StringContext> string() {
			return getRuleContexts(StringContext.class);
		}
		public StringContext string(int i) {
			return getRuleContext(StringContext.class,i);
		}
		public DomainNameOrIPv4HostContext(HostnameContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterDomainNameOrIPv4Host(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitDomainNameOrIPv4Host(this);
		}
	}

	public final HostnameContext hostname() throws RecognitionException {
		HostnameContext _localctx = new HostnameContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_hostname);
		int _la;
		try {
			setState(79);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case DIGITS:
			case STRING:
				_localctx = new DomainNameOrIPv4HostContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(67);
				string();
				setState(72);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__3) {
					{
					{
					setState(68);
					match(T__3);
					setState(69);
					string();
					}
					}
					setState(74);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case T__4:
				_localctx = new IPv6HostContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				match(T__4);
				setState(76);
				v6host();
				setState(77);
				match(T__5);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class V6hostContext extends ParserRuleContext {
		public List<StringContext> string() {
			return getRuleContexts(StringContext.class);
		}
		public StringContext string(int i) {
			return getRuleContext(StringContext.class,i);
		}
		public List<TerminalNode> DIGITS() { return getTokens(urlParser.DIGITS); }
		public TerminalNode DIGITS(int i) {
			return getToken(urlParser.DIGITS, i);
		}
		public V6hostContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_v6host; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterV6host(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitV6host(this);
		}
	}

	public final V6hostContext v6host() throws RecognitionException {
		V6hostContext _localctx = new V6hostContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_v6host);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__6) {
				{
				setState(81);
				match(T__6);
				}
			}

			setState(86);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(84);
				string();
				}
				break;
			case 2:
				{
				setState(85);
				match(DIGITS);
				}
				break;
			}
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1 || _la==T__6) {
				{
				{
				setState(88);
				_la = _input.LA(1);
				if ( !(_la==T__1 || _la==T__6) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(91);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
				case 1:
					{
					setState(89);
					string();
					}
					break;
				case 2:
					{
					setState(90);
					match(DIGITS);
					}
					break;
				}
				}
				}
				setState(97);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PortContext extends ParserRuleContext {
		public TerminalNode DIGITS() { return getToken(urlParser.DIGITS, 0); }
		public PortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_port; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterPort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitPort(this);
		}
	}

	public final PortContext port() throws RecognitionException {
		PortContext _localctx = new PortContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_port);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(DIGITS);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PathContext extends ParserRuleContext {
		public List<StringContext> string() {
			return getRuleContexts(StringContext.class);
		}
		public StringContext string(int i) {
			return getRuleContext(StringContext.class,i);
		}
		public PathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterPath(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitPath(this);
		}
	}

	public final PathContext path() throws RecognitionException {
		PathContext _localctx = new PathContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_path);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(100);
			string();
			setState(105);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(101);
					match(T__2);
					setState(102);
					string();
					}
					} 
				}
				setState(107);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,14,_ctx);
			}
			setState(109);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(108);
				match(T__2);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UserContext extends ParserRuleContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public UserContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_user; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterUser(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitUser(this);
		}
	}

	public final UserContext user() throws RecognitionException {
		UserContext _localctx = new UserContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_user);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(111);
			string();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LoginContext extends ParserRuleContext {
		public UserContext user() {
			return getRuleContext(UserContext.class,0);
		}
		public PasswordContext password() {
			return getRuleContext(PasswordContext.class,0);
		}
		public LoginContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_login; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterLogin(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitLogin(this);
		}
	}

	public final LoginContext login() throws RecognitionException {
		LoginContext _localctx = new LoginContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_login);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			user();
			setState(116);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(114);
				match(T__1);
				setState(115);
				password();
				}
			}

			setState(118);
			match(T__7);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PasswordContext extends ParserRuleContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public PasswordContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_password; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterPassword(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitPassword(this);
		}
	}

	public final PasswordContext password() throws RecognitionException {
		PasswordContext _localctx = new PasswordContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_password);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(120);
			string();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FragContext extends ParserRuleContext {
		public StringContext string() {
			return getRuleContext(StringContext.class,0);
		}
		public TerminalNode DIGITS() { return getToken(urlParser.DIGITS, 0); }
		public FragContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_frag; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterFrag(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitFrag(this);
		}
	}

	public final FragContext frag() throws RecognitionException {
		FragContext _localctx = new FragContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_frag);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(122);
			match(T__8);
			setState(125);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(123);
				string();
				}
				break;
			case 2:
				{
				setState(124);
				match(DIGITS);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QueryContext extends ParserRuleContext {
		public SearchContext search() {
			return getRuleContext(SearchContext.class,0);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterQuery(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitQuery(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_query);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			match(T__9);
			setState(128);
			search();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SearchContext extends ParserRuleContext {
		public List<SearchparameterContext> searchparameter() {
			return getRuleContexts(SearchparameterContext.class);
		}
		public SearchparameterContext searchparameter(int i) {
			return getRuleContext(SearchparameterContext.class,i);
		}
		public SearchContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_search; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterSearch(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitSearch(this);
		}
	}

	public final SearchContext search() throws RecognitionException {
		SearchContext _localctx = new SearchContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_search);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			searchparameter();
			setState(135);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__10) {
				{
				{
				setState(131);
				match(T__10);
				setState(132);
				searchparameter();
				}
				}
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SearchparameterContext extends ParserRuleContext {
		public List<StringContext> string() {
			return getRuleContexts(StringContext.class);
		}
		public StringContext string(int i) {
			return getRuleContext(StringContext.class,i);
		}
		public TerminalNode DIGITS() { return getToken(urlParser.DIGITS, 0); }
		public TerminalNode HEX() { return getToken(urlParser.HEX, 0); }
		public SearchparameterContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_searchparameter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterSearchparameter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitSearchparameter(this);
		}
	}

	public final SearchparameterContext searchparameter() throws RecognitionException {
		SearchparameterContext _localctx = new SearchparameterContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_searchparameter);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			string();
			setState(145);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__11) {
				{
				setState(139);
				match(T__11);
				setState(143);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
				case 1:
					{
					setState(140);
					string();
					}
					break;
				case 2:
					{
					setState(141);
					match(DIGITS);
					}
					break;
				case 3:
					{
					setState(142);
					match(HEX);
					}
					break;
				}
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StringContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(urlParser.STRING, 0); }
		public TerminalNode DIGITS() { return getToken(urlParser.DIGITS, 0); }
		public StringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).enterString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof urlListener ) ((urlListener)listener).exitString(this);
		}
	}

	public final StringContext string() throws RecognitionException {
		StringContext _localctx = new StringContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_string);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(147);
			_la = _input.LA(1);
			if ( !(_la==DIGITS || _la==STRING) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\22\u0098\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2"+
		"\3\2\3\3\3\3\3\3\5\3)\n\3\3\3\3\3\3\3\5\3.\n\3\3\3\3\3\5\3\62\n\3\5\3"+
		"\64\n\3\3\3\5\3\67\n\3\3\3\5\3:\n\3\3\3\5\3=\n\3\3\4\3\4\3\5\5\5B\n\5"+
		"\3\5\3\5\3\6\3\6\3\6\7\6I\n\6\f\6\16\6L\13\6\3\6\3\6\3\6\3\6\5\6R\n\6"+
		"\3\7\5\7U\n\7\3\7\3\7\5\7Y\n\7\3\7\3\7\3\7\5\7^\n\7\7\7`\n\7\f\7\16\7"+
		"c\13\7\3\b\3\b\3\t\3\t\3\t\7\tj\n\t\f\t\16\tm\13\t\3\t\5\tp\n\t\3\n\3"+
		"\n\3\13\3\13\3\13\5\13w\n\13\3\13\3\13\3\f\3\f\3\r\3\r\3\r\5\r\u0080\n"+
		"\r\3\16\3\16\3\16\3\17\3\17\3\17\7\17\u0088\n\17\f\17\16\17\u008b\13\17"+
		"\3\20\3\20\3\20\3\20\3\20\5\20\u0092\n\20\5\20\u0094\n\20\3\21\3\21\3"+
		"\21\2\2\22\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \2\4\4\2\4\4\t\t\4\2"+
		"\17\17\21\21\2\u009d\2\"\3\2\2\2\4%\3\2\2\2\6>\3\2\2\2\bA\3\2\2\2\nQ\3"+
		"\2\2\2\fT\3\2\2\2\16d\3\2\2\2\20f\3\2\2\2\22q\3\2\2\2\24s\3\2\2\2\26z"+
		"\3\2\2\2\30|\3\2\2\2\32\u0081\3\2\2\2\34\u0084\3\2\2\2\36\u008c\3\2\2"+
		"\2 \u0095\3\2\2\2\"#\5\4\3\2#$\7\2\2\3$\3\3\2\2\2%&\5\6\4\2&(\7\3\2\2"+
		"\')\5\24\13\2(\'\3\2\2\2()\3\2\2\2)*\3\2\2\2*-\5\b\5\2+,\7\4\2\2,.\5\16"+
		"\b\2-+\3\2\2\2-.\3\2\2\2.\63\3\2\2\2/\61\7\5\2\2\60\62\5\20\t\2\61\60"+
		"\3\2\2\2\61\62\3\2\2\2\62\64\3\2\2\2\63/\3\2\2\2\63\64\3\2\2\2\64\66\3"+
		"\2\2\2\65\67\5\32\16\2\66\65\3\2\2\2\66\67\3\2\2\2\679\3\2\2\28:\5\30"+
		"\r\298\3\2\2\29:\3\2\2\2:<\3\2\2\2;=\7\22\2\2<;\3\2\2\2<=\3\2\2\2=\5\3"+
		"\2\2\2>?\5 \21\2?\7\3\2\2\2@B\7\5\2\2A@\3\2\2\2AB\3\2\2\2BC\3\2\2\2CD"+
		"\5\n\6\2D\t\3\2\2\2EJ\5 \21\2FG\7\6\2\2GI\5 \21\2HF\3\2\2\2IL\3\2\2\2"+
		"JH\3\2\2\2JK\3\2\2\2KR\3\2\2\2LJ\3\2\2\2MN\7\7\2\2NO\5\f\7\2OP\7\b\2\2"+
		"PR\3\2\2\2QE\3\2\2\2QM\3\2\2\2R\13\3\2\2\2SU\7\t\2\2TS\3\2\2\2TU\3\2\2"+
		"\2UX\3\2\2\2VY\5 \21\2WY\7\17\2\2XV\3\2\2\2XW\3\2\2\2Ya\3\2\2\2Z]\t\2"+
		"\2\2[^\5 \21\2\\^\7\17\2\2][\3\2\2\2]\\\3\2\2\2^`\3\2\2\2_Z\3\2\2\2`c"+
		"\3\2\2\2a_\3\2\2\2ab\3\2\2\2b\r\3\2\2\2ca\3\2\2\2de\7\17\2\2e\17\3\2\2"+
		"\2fk\5 \21\2gh\7\5\2\2hj\5 \21\2ig\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2"+
		"\2lo\3\2\2\2mk\3\2\2\2np\7\5\2\2on\3\2\2\2op\3\2\2\2p\21\3\2\2\2qr\5 "+
		"\21\2r\23\3\2\2\2sv\5\22\n\2tu\7\4\2\2uw\5\26\f\2vt\3\2\2\2vw\3\2\2\2"+
		"wx\3\2\2\2xy\7\n\2\2y\25\3\2\2\2z{\5 \21\2{\27\3\2\2\2|\177\7\13\2\2}"+
		"\u0080\5 \21\2~\u0080\7\17\2\2\177}\3\2\2\2\177~\3\2\2\2\u0080\31\3\2"+
		"\2\2\u0081\u0082\7\f\2\2\u0082\u0083\5\34\17\2\u0083\33\3\2\2\2\u0084"+
		"\u0089\5\36\20\2\u0085\u0086\7\r\2\2\u0086\u0088\5\36\20\2\u0087\u0085"+
		"\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2\2\2\u0089\u008a\3\2\2\2\u008a"+
		"\35\3\2\2\2\u008b\u0089\3\2\2\2\u008c\u0093\5 \21\2\u008d\u0091\7\16\2"+
		"\2\u008e\u0092\5 \21\2\u008f\u0092\7\17\2\2\u0090\u0092\7\20\2\2\u0091"+
		"\u008e\3\2\2\2\u0091\u008f\3\2\2\2\u0091\u0090\3\2\2\2\u0092\u0094\3\2"+
		"\2\2\u0093\u008d\3\2\2\2\u0093\u0094\3\2\2\2\u0094\37\3\2\2\2\u0095\u0096"+
		"\t\3\2\2\u0096!\3\2\2\2\27(-\61\63\669<AJQTX]akov\177\u0089\u0091\u0093";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}