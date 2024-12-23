// Generated from romannumerals.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class romannumeralsLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		M=1, CD=2, D=3, CM=4, C=5, CC=6, CCC=7, XL=8, L=9, XC=10, X=11, XX=12, 
		XXX=13, IV=14, V=15, IX=16, I=17, II=18, III=19, WS=20;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"M", "CD", "D", "CM", "C", "CC", "CCC", "XL", "L", "XC", "X", "XX", "XXX", 
			"IV", "V", "IX", "I", "II", "III", "WS"
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


	public romannumeralsLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "romannumerals.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\26g\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\3\3\3\3\3\3\4\3\4\3\5\3\5\3\5"+
		"\3\6\3\6\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\13"+
		"\3\f\3\f\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\21"+
		"\3\21\3\21\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\6\25b\n\25"+
		"\r\25\16\25c\3\25\3\25\2\2\26\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13"+
		"\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26\3\2\3\5\2\13\f"+
		"\17\17\"\"\2g\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2"+
		"\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\3+\3\2\2\2\5-\3\2\2"+
		"\2\7\60\3\2\2\2\t\62\3\2\2\2\13\65\3\2\2\2\r\67\3\2\2\2\17:\3\2\2\2\21"+
		">\3\2\2\2\23A\3\2\2\2\25C\3\2\2\2\27F\3\2\2\2\31H\3\2\2\2\33K\3\2\2\2"+
		"\35O\3\2\2\2\37R\3\2\2\2!T\3\2\2\2#W\3\2\2\2%Y\3\2\2\2\'\\\3\2\2\2)a\3"+
		"\2\2\2+,\7O\2\2,\4\3\2\2\2-.\7E\2\2./\7F\2\2/\6\3\2\2\2\60\61\7F\2\2\61"+
		"\b\3\2\2\2\62\63\7E\2\2\63\64\7O\2\2\64\n\3\2\2\2\65\66\7E\2\2\66\f\3"+
		"\2\2\2\678\7E\2\289\7E\2\29\16\3\2\2\2:;\7E\2\2;<\7E\2\2<=\7E\2\2=\20"+
		"\3\2\2\2>?\7Z\2\2?@\7N\2\2@\22\3\2\2\2AB\7N\2\2B\24\3\2\2\2CD\7Z\2\2D"+
		"E\7E\2\2E\26\3\2\2\2FG\7Z\2\2G\30\3\2\2\2HI\7Z\2\2IJ\7Z\2\2J\32\3\2\2"+
		"\2KL\7Z\2\2LM\7Z\2\2MN\7Z\2\2N\34\3\2\2\2OP\7K\2\2PQ\7X\2\2Q\36\3\2\2"+
		"\2RS\7X\2\2S \3\2\2\2TU\7K\2\2UV\7Z\2\2V\"\3\2\2\2WX\7K\2\2X$\3\2\2\2"+
		"YZ\7K\2\2Z[\7K\2\2[&\3\2\2\2\\]\7K\2\2]^\7K\2\2^_\7K\2\2_(\3\2\2\2`b\t"+
		"\2\2\2a`\3\2\2\2bc\3\2\2\2ca\3\2\2\2cd\3\2\2\2de\3\2\2\2ef\b\25\2\2f*"+
		"\3\2\2\2\4\2c\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}