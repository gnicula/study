// Generated from romannumerals.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class romannumeralsParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		M=1, CD=2, D=3, CM=4, C=5, CC=6, CCC=7, XL=8, L=9, XC=10, X=11, XX=12, 
		XXX=13, IV=14, V=15, IX=16, I=17, II=18, III=19, WS=20;
	public static final int
		RULE_expression = 0, RULE_thousands = 1, RULE_thous_part = 2, RULE_hundreds = 3, 
		RULE_hun_part = 4, RULE_hun_rep = 5, RULE_tens = 6, RULE_tens_part = 7, 
		RULE_tens_rep = 8, RULE_ones = 9, RULE_ones_rep = 10;
	private static String[] makeRuleNames() {
		return new String[] {
			"expression", "thousands", "thous_part", "hundreds", "hun_part", "hun_rep", 
			"tens", "tens_part", "tens_rep", "ones", "ones_rep"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'M'", "'CD'", "'D'", "'CM'", "'C'", "'CC'", "'CCC'", "'XL'", "'L'", 
			"'XC'", "'X'", "'XX'", "'XXX'", "'IV'", "'V'", "'IX'", "'I'", "'II'", 
			"'III'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "M", "CD", "D", "CM", "C", "CC", "CCC", "XL", "L", "XC", "X", "XX", 
			"XXX", "IV", "V", "IX", "I", "II", "III", "WS"
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
	public String getGrammarFileName() { return "romannumerals.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public romannumeralsParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ExpressionContext extends ParserRuleContext {
		public ThousandsContext thousands() {
			return getRuleContext(ThousandsContext.class,0);
		}
		public TerminalNode EOF() { return getToken(romannumeralsParser.EOF, 0); }
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(22);
			thousands();
			setState(23);
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

	public static class ThousandsContext extends ParserRuleContext {
		public Thous_partContext thous_part() {
			return getRuleContext(Thous_partContext.class,0);
		}
		public HundredsContext hundreds() {
			return getRuleContext(HundredsContext.class,0);
		}
		public ThousandsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_thousands; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).enterThousands(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).exitThousands(this);
		}
	}

	public final ThousandsContext thousands() throws RecognitionException {
		ThousandsContext _localctx = new ThousandsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_thousands);
		try {
			setState(30);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(25);
				thous_part(0);
				setState(26);
				hundreds();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(28);
				thous_part(0);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(29);
				hundreds();
				}
				break;
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

	public static class Thous_partContext extends ParserRuleContext {
		public TerminalNode M() { return getToken(romannumeralsParser.M, 0); }
		public Thous_partContext thous_part() {
			return getRuleContext(Thous_partContext.class,0);
		}
		public Thous_partContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_thous_part; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).enterThous_part(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).exitThous_part(this);
		}
	}

	public final Thous_partContext thous_part() throws RecognitionException {
		return thous_part(0);
	}

	private Thous_partContext thous_part(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Thous_partContext _localctx = new Thous_partContext(_ctx, _parentState);
		Thous_partContext _prevctx = _localctx;
		int _startState = 4;
		enterRecursionRule(_localctx, 4, RULE_thous_part, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			{
			setState(33);
			match(M);
			}
			_ctx.stop = _input.LT(-1);
			setState(39);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new Thous_partContext(_parentctx, _parentState);
					pushNewRecursionContext(_localctx, _startState, RULE_thous_part);
					setState(35);
					if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
					setState(36);
					match(M);
					}
					} 
				}
				setState(41);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class HundredsContext extends ParserRuleContext {
		public Hun_partContext hun_part() {
			return getRuleContext(Hun_partContext.class,0);
		}
		public TensContext tens() {
			return getRuleContext(TensContext.class,0);
		}
		public HundredsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hundreds; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).enterHundreds(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).exitHundreds(this);
		}
	}

	public final HundredsContext hundreds() throws RecognitionException {
		HundredsContext _localctx = new HundredsContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_hundreds);
		try {
			setState(47);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(42);
				hun_part();
				setState(43);
				tens();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(45);
				hun_part();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(46);
				tens();
				}
				break;
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

	public static class Hun_partContext extends ParserRuleContext {
		public Hun_repContext hun_rep() {
			return getRuleContext(Hun_repContext.class,0);
		}
		public TerminalNode CD() { return getToken(romannumeralsParser.CD, 0); }
		public TerminalNode D() { return getToken(romannumeralsParser.D, 0); }
		public TerminalNode CM() { return getToken(romannumeralsParser.CM, 0); }
		public Hun_partContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hun_part; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).enterHun_part(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).exitHun_part(this);
		}
	}

	public final Hun_partContext hun_part() throws RecognitionException {
		Hun_partContext _localctx = new Hun_partContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_hun_part);
		try {
			setState(55);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(49);
				hun_rep();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(50);
				match(CD);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(51);
				match(D);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(52);
				match(D);
				setState(53);
				hun_rep();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(54);
				match(CM);
				}
				break;
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

	public static class Hun_repContext extends ParserRuleContext {
		public TerminalNode C() { return getToken(romannumeralsParser.C, 0); }
		public TerminalNode CC() { return getToken(romannumeralsParser.CC, 0); }
		public TerminalNode CCC() { return getToken(romannumeralsParser.CCC, 0); }
		public Hun_repContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_hun_rep; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).enterHun_rep(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).exitHun_rep(this);
		}
	}

	public final Hun_repContext hun_rep() throws RecognitionException {
		Hun_repContext _localctx = new Hun_repContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_hun_rep);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(57);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << C) | (1L << CC) | (1L << CCC))) != 0)) ) {
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

	public static class TensContext extends ParserRuleContext {
		public Tens_partContext tens_part() {
			return getRuleContext(Tens_partContext.class,0);
		}
		public OnesContext ones() {
			return getRuleContext(OnesContext.class,0);
		}
		public TensContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tens; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).enterTens(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).exitTens(this);
		}
	}

	public final TensContext tens() throws RecognitionException {
		TensContext _localctx = new TensContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_tens);
		try {
			setState(64);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(59);
				tens_part();
				setState(60);
				ones();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(62);
				tens_part();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(63);
				ones();
				}
				break;
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

	public static class Tens_partContext extends ParserRuleContext {
		public Tens_repContext tens_rep() {
			return getRuleContext(Tens_repContext.class,0);
		}
		public TerminalNode XL() { return getToken(romannumeralsParser.XL, 0); }
		public TerminalNode L() { return getToken(romannumeralsParser.L, 0); }
		public TerminalNode XC() { return getToken(romannumeralsParser.XC, 0); }
		public Tens_partContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tens_part; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).enterTens_part(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).exitTens_part(this);
		}
	}

	public final Tens_partContext tens_part() throws RecognitionException {
		Tens_partContext _localctx = new Tens_partContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_tens_part);
		try {
			setState(72);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(66);
				tens_rep();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(67);
				match(XL);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(68);
				match(L);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(69);
				match(L);
				setState(70);
				tens_rep();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(71);
				match(XC);
				}
				break;
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

	public static class Tens_repContext extends ParserRuleContext {
		public TerminalNode X() { return getToken(romannumeralsParser.X, 0); }
		public TerminalNode XX() { return getToken(romannumeralsParser.XX, 0); }
		public TerminalNode XXX() { return getToken(romannumeralsParser.XXX, 0); }
		public Tens_repContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tens_rep; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).enterTens_rep(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).exitTens_rep(this);
		}
	}

	public final Tens_repContext tens_rep() throws RecognitionException {
		Tens_repContext _localctx = new Tens_repContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_tens_rep);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(74);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << X) | (1L << XX) | (1L << XXX))) != 0)) ) {
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

	public static class OnesContext extends ParserRuleContext {
		public Ones_repContext ones_rep() {
			return getRuleContext(Ones_repContext.class,0);
		}
		public TerminalNode IV() { return getToken(romannumeralsParser.IV, 0); }
		public TerminalNode V() { return getToken(romannumeralsParser.V, 0); }
		public TerminalNode IX() { return getToken(romannumeralsParser.IX, 0); }
		public OnesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ones; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).enterOnes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).exitOnes(this);
		}
	}

	public final OnesContext ones() throws RecognitionException {
		OnesContext _localctx = new OnesContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_ones);
		try {
			setState(82);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(76);
				ones_rep();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(77);
				match(IV);
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(78);
				match(V);
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(79);
				match(V);
				setState(80);
				ones_rep();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(81);
				match(IX);
				}
				break;
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

	public static class Ones_repContext extends ParserRuleContext {
		public TerminalNode I() { return getToken(romannumeralsParser.I, 0); }
		public TerminalNode II() { return getToken(romannumeralsParser.II, 0); }
		public TerminalNode III() { return getToken(romannumeralsParser.III, 0); }
		public Ones_repContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ones_rep; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).enterOnes_rep(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof romannumeralsListener ) ((romannumeralsListener)listener).exitOnes_rep(this);
		}
	}

	public final Ones_repContext ones_rep() throws RecognitionException {
		Ones_repContext _localctx = new Ones_repContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_ones_rep);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << I) | (1L << II) | (1L << III))) != 0)) ) {
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 2:
			return thous_part_sempred((Thous_partContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean thous_part_sempred(Thous_partContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\26Y\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\5\3!\n\3\3\4\3\4\3\4\3\4\3\4\7"+
		"\4(\n\4\f\4\16\4+\13\4\3\5\3\5\3\5\3\5\3\5\5\5\62\n\5\3\6\3\6\3\6\3\6"+
		"\3\6\3\6\5\6:\n\6\3\7\3\7\3\b\3\b\3\b\3\b\3\b\5\bC\n\b\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\5\tK\n\t\3\n\3\n\3\13\3\13\3\13\3\13\3\13\3\13\5\13U\n\13\3\f"+
		"\3\f\3\f\2\3\6\r\2\4\6\b\n\f\16\20\22\24\26\2\5\3\2\7\t\3\2\r\17\3\2\23"+
		"\25\2`\2\30\3\2\2\2\4 \3\2\2\2\6\"\3\2\2\2\b\61\3\2\2\2\n9\3\2\2\2\f;"+
		"\3\2\2\2\16B\3\2\2\2\20J\3\2\2\2\22L\3\2\2\2\24T\3\2\2\2\26V\3\2\2\2\30"+
		"\31\5\4\3\2\31\32\7\2\2\3\32\3\3\2\2\2\33\34\5\6\4\2\34\35\5\b\5\2\35"+
		"!\3\2\2\2\36!\5\6\4\2\37!\5\b\5\2 \33\3\2\2\2 \36\3\2\2\2 \37\3\2\2\2"+
		"!\5\3\2\2\2\"#\b\4\1\2#$\7\3\2\2$)\3\2\2\2%&\f\4\2\2&(\7\3\2\2\'%\3\2"+
		"\2\2(+\3\2\2\2)\'\3\2\2\2)*\3\2\2\2*\7\3\2\2\2+)\3\2\2\2,-\5\n\6\2-.\5"+
		"\16\b\2.\62\3\2\2\2/\62\5\n\6\2\60\62\5\16\b\2\61,\3\2\2\2\61/\3\2\2\2"+
		"\61\60\3\2\2\2\62\t\3\2\2\2\63:\5\f\7\2\64:\7\4\2\2\65:\7\5\2\2\66\67"+
		"\7\5\2\2\67:\5\f\7\28:\7\6\2\29\63\3\2\2\29\64\3\2\2\29\65\3\2\2\29\66"+
		"\3\2\2\298\3\2\2\2:\13\3\2\2\2;<\t\2\2\2<\r\3\2\2\2=>\5\20\t\2>?\5\24"+
		"\13\2?C\3\2\2\2@C\5\20\t\2AC\5\24\13\2B=\3\2\2\2B@\3\2\2\2BA\3\2\2\2C"+
		"\17\3\2\2\2DK\5\22\n\2EK\7\n\2\2FK\7\13\2\2GH\7\13\2\2HK\5\22\n\2IK\7"+
		"\f\2\2JD\3\2\2\2JE\3\2\2\2JF\3\2\2\2JG\3\2\2\2JI\3\2\2\2K\21\3\2\2\2L"+
		"M\t\3\2\2M\23\3\2\2\2NU\5\26\f\2OU\7\20\2\2PU\7\21\2\2QR\7\21\2\2RU\5"+
		"\26\f\2SU\7\22\2\2TN\3\2\2\2TO\3\2\2\2TP\3\2\2\2TQ\3\2\2\2TS\3\2\2\2U"+
		"\25\3\2\2\2VW\t\4\2\2W\27\3\2\2\2\t )\619BJT";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}