// Generated from /Users/jagalindo/Documents/uvl-parser-java/antlr4-grammar/UVL.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class UVLParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, INDENT=24, 
		DEDENT=25, ORGROUP=26, ALTERNATIVE=27, OPTIONAL=28, MANDATORY=29, CARDINALITY=30, 
		NOT=31, AND=32, OR=33, EQUIVALENCE=34, IMPLICATION=35, EQUAL=36, LOWER=37, 
		LOWER_EQUALS=38, GREATER=39, GREATER_EQUALS=40, NOT_EQUALS=41, DIV=42, 
		MUL=43, ADD=44, SUB=45, FLOAT=46, INTEGER=47, BOOLEAN=48, BOOLEAN_KEY=49, 
		COMMA=50, OPEN_PAREN=51, CLOSE_PAREN=52, OPEN_BRACK=53, CLOSE_BRACK=54, 
		OPEN_BRACE=55, CLOSE_BRACE=56, OPEN_COMMENT=57, CLOSE_COMMENT=58, ID_NOT_STRICT=59, 
		ID_STRICT=60, STRING=61, NEWLINE=62, SKIP_=63;
	public static final int
		RULE_featureModel = 0, RULE_includes = 1, RULE_includeLine = 2, RULE_namespace = 3, 
		RULE_imports = 4, RULE_importLine = 5, RULE_features = 6, RULE_group = 7, 
		RULE_groupSpec = 8, RULE_feature = 9, RULE_featureCardinality = 10, RULE_attributes = 11, 
		RULE_attribute = 12, RULE_valueAttribute = 13, RULE_key = 14, RULE_value = 15, 
		RULE_vector = 16, RULE_constraintAttribute = 17, RULE_constraintList = 18, 
		RULE_constraints = 19, RULE_constraintLine = 20, RULE_constraint = 21, 
		RULE_equation = 22, RULE_expression = 23, RULE_aggregateFunction = 24, 
		RULE_stringAggregateFunction = 25, RULE_numericAggregateFunction = 26, 
		RULE_reference = 27, RULE_id = 28, RULE_featureType = 29, RULE_languageLevel = 30, 
		RULE_majorLevel = 31, RULE_minorLevel = 32;
	private static String[] makeRuleNames() {
		return new String[] {
			"featureModel", "includes", "includeLine", "namespace", "imports", "importLine", 
			"features", "group", "groupSpec", "feature", "featureCardinality", "attributes", 
			"attribute", "valueAttribute", "key", "value", "vector", "constraintAttribute", 
			"constraintList", "constraints", "constraintLine", "constraint", "equation", 
			"expression", "aggregateFunction", "stringAggregateFunction", "numericAggregateFunction", 
			"reference", "id", "featureType", "languageLevel", "majorLevel", "minorLevel"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'include'", "'namespace'", "'imports'", "'as'", "'features'", 
			"'cardinality'", "'constraint'", "'constraints'", "'sum'", "'avg'", "'len'", 
			"'floor'", "'ceil'", "'.'", "'String'", "'Integer'", "'Real'", "'Arithmetic'", 
			"'Type'", "'group-cardinality'", "'feature-cardinality'", "'aggregate-function'", 
			"'string-constraints'", "'<INDENT>'", "'<DEDENT>'", "'or'", "'alternative'", 
			"'optional'", "'mandatory'", null, "'!'", "'&'", "'|'", "'<=>'", "'=>'", 
			"'=='", "'<'", "'<='", "'>'", "'>='", "'!='", "'/'", "'*'", "'+'", "'-'", 
			null, null, null, "'Boolean'", "','", "'('", "')'", "'['", "']'", "'{'", 
			"'}'", "'/*'", "'*/'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			"INDENT", "DEDENT", "ORGROUP", "ALTERNATIVE", "OPTIONAL", "MANDATORY", 
			"CARDINALITY", "NOT", "AND", "OR", "EQUIVALENCE", "IMPLICATION", "EQUAL", 
			"LOWER", "LOWER_EQUALS", "GREATER", "GREATER_EQUALS", "NOT_EQUALS", "DIV", 
			"MUL", "ADD", "SUB", "FLOAT", "INTEGER", "BOOLEAN", "BOOLEAN_KEY", "COMMA", 
			"OPEN_PAREN", "CLOSE_PAREN", "OPEN_BRACK", "CLOSE_BRACK", "OPEN_BRACE", 
			"CLOSE_BRACE", "OPEN_COMMENT", "CLOSE_COMMENT", "ID_NOT_STRICT", "ID_STRICT", 
			"STRING", "NEWLINE", "SKIP_"
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
	public String getGrammarFileName() { return "UVL.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public UVLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FeatureModelContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(UVLParser.EOF, 0); }
		public NamespaceContext namespace() {
			return getRuleContext(NamespaceContext.class,0);
		}
		public List<TerminalNode> NEWLINE() { return getTokens(UVLParser.NEWLINE); }
		public TerminalNode NEWLINE(int i) {
			return getToken(UVLParser.NEWLINE, i);
		}
		public IncludesContext includes() {
			return getRuleContext(IncludesContext.class,0);
		}
		public ImportsContext imports() {
			return getRuleContext(ImportsContext.class,0);
		}
		public FeaturesContext features() {
			return getRuleContext(FeaturesContext.class,0);
		}
		public ConstraintsContext constraints() {
			return getRuleContext(ConstraintsContext.class,0);
		}
		public FeatureModelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_featureModel; }
	}

	public final FeatureModelContext featureModel() throws RecognitionException {
		FeatureModelContext _localctx = new FeatureModelContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_featureModel);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(66);
				namespace();
				}
			}

			setState(70);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,1,_ctx) ) {
			case 1:
				{
				setState(69);
				match(NEWLINE);
				}
				break;
			}
			setState(73);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__0) {
				{
				setState(72);
				includes();
				}
			}

			setState(76);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				{
				setState(75);
				match(NEWLINE);
				}
				break;
			}
			setState(79);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(78);
				imports();
				}
			}

			setState(82);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(81);
				match(NEWLINE);
				}
				break;
			}
			setState(85);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__4) {
				{
				setState(84);
				features();
				}
			}

			setState(88);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NEWLINE) {
				{
				setState(87);
				match(NEWLINE);
				}
			}

			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(90);
				constraints();
				}
			}

			setState(93);
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

	@SuppressWarnings("CheckReturnValue")
	public static class IncludesContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(UVLParser.NEWLINE, 0); }
		public TerminalNode INDENT() { return getToken(UVLParser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(UVLParser.DEDENT, 0); }
		public List<IncludeLineContext> includeLine() {
			return getRuleContexts(IncludeLineContext.class);
		}
		public IncludeLineContext includeLine(int i) {
			return getRuleContext(IncludeLineContext.class,i);
		}
		public IncludesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_includes; }
	}

	public final IncludesContext includes() throws RecognitionException {
		IncludesContext _localctx = new IncludesContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_includes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(95);
			match(T__0);
			setState(96);
			match(NEWLINE);
			setState(97);
			match(INDENT);
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 562949954207744L) != 0)) {
				{
				{
				setState(98);
				includeLine();
				}
				}
				setState(103);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(104);
			match(DEDENT);
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

	@SuppressWarnings("CheckReturnValue")
	public static class IncludeLineContext extends ParserRuleContext {
		public LanguageLevelContext languageLevel() {
			return getRuleContext(LanguageLevelContext.class,0);
		}
		public TerminalNode NEWLINE() { return getToken(UVLParser.NEWLINE, 0); }
		public IncludeLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_includeLine; }
	}

	public final IncludeLineContext includeLine() throws RecognitionException {
		IncludeLineContext _localctx = new IncludeLineContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_includeLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			languageLevel();
			setState(107);
			match(NEWLINE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class NamespaceContext extends ParserRuleContext {
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public NamespaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_namespace; }
	}

	public final NamespaceContext namespace() throws RecognitionException {
		NamespaceContext _localctx = new NamespaceContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_namespace);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			match(T__1);
			setState(110);
			reference();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ImportsContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(UVLParser.NEWLINE, 0); }
		public TerminalNode INDENT() { return getToken(UVLParser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(UVLParser.DEDENT, 0); }
		public List<ImportLineContext> importLine() {
			return getRuleContexts(ImportLineContext.class);
		}
		public ImportLineContext importLine(int i) {
			return getRuleContext(ImportLineContext.class,i);
		}
		public ImportsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_imports; }
	}

	public final ImportsContext imports() throws RecognitionException {
		ImportsContext _localctx = new ImportsContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_imports);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(T__2);
			setState(113);
			match(NEWLINE);
			setState(114);
			match(INDENT);
			setState(118);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ID_NOT_STRICT || _la==ID_STRICT) {
				{
				{
				setState(115);
				importLine();
				}
				}
				setState(120);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(121);
			match(DEDENT);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ImportLineContext extends ParserRuleContext {
		public ReferenceContext ns;
		public ReferenceContext alias;
		public TerminalNode NEWLINE() { return getToken(UVLParser.NEWLINE, 0); }
		public List<ReferenceContext> reference() {
			return getRuleContexts(ReferenceContext.class);
		}
		public ReferenceContext reference(int i) {
			return getRuleContext(ReferenceContext.class,i);
		}
		public ImportLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importLine; }
	}

	public final ImportLineContext importLine() throws RecognitionException {
		ImportLineContext _localctx = new ImportLineContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_importLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123);
			((ImportLineContext)_localctx).ns = reference();
			setState(126);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(124);
				match(T__3);
				setState(125);
				((ImportLineContext)_localctx).alias = reference();
				}
			}

			setState(128);
			match(NEWLINE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class FeaturesContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(UVLParser.NEWLINE, 0); }
		public TerminalNode INDENT() { return getToken(UVLParser.INDENT, 0); }
		public FeatureContext feature() {
			return getRuleContext(FeatureContext.class,0);
		}
		public TerminalNode DEDENT() { return getToken(UVLParser.DEDENT, 0); }
		public FeaturesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_features; }
	}

	public final FeaturesContext features() throws RecognitionException {
		FeaturesContext _localctx = new FeaturesContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_features);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(130);
			match(T__4);
			setState(131);
			match(NEWLINE);
			setState(132);
			match(INDENT);
			setState(133);
			feature();
			setState(134);
			match(DEDENT);
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

	@SuppressWarnings("CheckReturnValue")
	public static class GroupContext extends ParserRuleContext {
		public GroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group; }
	 
		public GroupContext() { }
		public void copyFrom(GroupContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AlternativeGroupContext extends GroupContext {
		public TerminalNode ALTERNATIVE() { return getToken(UVLParser.ALTERNATIVE, 0); }
		public GroupSpecContext groupSpec() {
			return getRuleContext(GroupSpecContext.class,0);
		}
		public AlternativeGroupContext(GroupContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OptionalGroupContext extends GroupContext {
		public TerminalNode OPTIONAL() { return getToken(UVLParser.OPTIONAL, 0); }
		public GroupSpecContext groupSpec() {
			return getRuleContext(GroupSpecContext.class,0);
		}
		public OptionalGroupContext(GroupContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MandatoryGroupContext extends GroupContext {
		public TerminalNode MANDATORY() { return getToken(UVLParser.MANDATORY, 0); }
		public GroupSpecContext groupSpec() {
			return getRuleContext(GroupSpecContext.class,0);
		}
		public MandatoryGroupContext(GroupContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CardinalityGroupContext extends GroupContext {
		public TerminalNode CARDINALITY() { return getToken(UVLParser.CARDINALITY, 0); }
		public GroupSpecContext groupSpec() {
			return getRuleContext(GroupSpecContext.class,0);
		}
		public CardinalityGroupContext(GroupContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrGroupContext extends GroupContext {
		public TerminalNode ORGROUP() { return getToken(UVLParser.ORGROUP, 0); }
		public GroupSpecContext groupSpec() {
			return getRuleContext(GroupSpecContext.class,0);
		}
		public OrGroupContext(GroupContext ctx) { copyFrom(ctx); }
	}

	public final GroupContext group() throws RecognitionException {
		GroupContext _localctx = new GroupContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_group);
		try {
			setState(146);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ORGROUP:
				_localctx = new OrGroupContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(136);
				match(ORGROUP);
				setState(137);
				groupSpec();
				}
				break;
			case ALTERNATIVE:
				_localctx = new AlternativeGroupContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(138);
				match(ALTERNATIVE);
				setState(139);
				groupSpec();
				}
				break;
			case OPTIONAL:
				_localctx = new OptionalGroupContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(140);
				match(OPTIONAL);
				setState(141);
				groupSpec();
				}
				break;
			case MANDATORY:
				_localctx = new MandatoryGroupContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(142);
				match(MANDATORY);
				setState(143);
				groupSpec();
				}
				break;
			case CARDINALITY:
				_localctx = new CardinalityGroupContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(144);
				match(CARDINALITY);
				setState(145);
				groupSpec();
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

	@SuppressWarnings("CheckReturnValue")
	public static class GroupSpecContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(UVLParser.NEWLINE, 0); }
		public TerminalNode INDENT() { return getToken(UVLParser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(UVLParser.DEDENT, 0); }
		public List<FeatureContext> feature() {
			return getRuleContexts(FeatureContext.class);
		}
		public FeatureContext feature(int i) {
			return getRuleContext(FeatureContext.class,i);
		}
		public GroupSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupSpec; }
	}

	public final GroupSpecContext groupSpec() throws RecognitionException {
		GroupSpecContext _localctx = new GroupSpecContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_groupSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			match(NEWLINE);
			setState(149);
			match(INDENT);
			setState(151); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(150);
				feature();
				}
				}
				setState(153); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 1729945206863921152L) != 0) );
			setState(155);
			match(DEDENT);
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

	@SuppressWarnings("CheckReturnValue")
	public static class FeatureContext extends ParserRuleContext {
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public TerminalNode NEWLINE() { return getToken(UVLParser.NEWLINE, 0); }
		public FeatureTypeContext featureType() {
			return getRuleContext(FeatureTypeContext.class,0);
		}
		public FeatureCardinalityContext featureCardinality() {
			return getRuleContext(FeatureCardinalityContext.class,0);
		}
		public AttributesContext attributes() {
			return getRuleContext(AttributesContext.class,0);
		}
		public TerminalNode INDENT() { return getToken(UVLParser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(UVLParser.DEDENT, 0); }
		public List<GroupContext> group() {
			return getRuleContexts(GroupContext.class);
		}
		public GroupContext group(int i) {
			return getRuleContext(GroupContext.class,i);
		}
		public FeatureContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_feature; }
	}

	public final FeatureContext feature() throws RecognitionException {
		FeatureContext _localctx = new FeatureContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_feature);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 562949953650688L) != 0)) {
				{
				setState(157);
				featureType();
				}
			}

			setState(160);
			reference();
			setState(162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__5) {
				{
				setState(161);
				featureCardinality();
				}
			}

			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==OPEN_BRACE) {
				{
				setState(164);
				attributes();
				}
			}

			setState(167);
			match(NEWLINE);
			setState(176);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==INDENT) {
				{
				setState(168);
				match(INDENT);
				setState(170); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(169);
					group();
					}
					}
					setState(172); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 2080374784L) != 0) );
				setState(174);
				match(DEDENT);
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

	@SuppressWarnings("CheckReturnValue")
	public static class FeatureCardinalityContext extends ParserRuleContext {
		public TerminalNode CARDINALITY() { return getToken(UVLParser.CARDINALITY, 0); }
		public FeatureCardinalityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_featureCardinality; }
	}

	public final FeatureCardinalityContext featureCardinality() throws RecognitionException {
		FeatureCardinalityContext _localctx = new FeatureCardinalityContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_featureCardinality);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			match(T__5);
			setState(179);
			match(CARDINALITY);
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

	@SuppressWarnings("CheckReturnValue")
	public static class AttributesContext extends ParserRuleContext {
		public TerminalNode OPEN_BRACE() { return getToken(UVLParser.OPEN_BRACE, 0); }
		public TerminalNode CLOSE_BRACE() { return getToken(UVLParser.CLOSE_BRACE, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(UVLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(UVLParser.COMMA, i);
		}
		public AttributesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributes; }
	}

	public final AttributesContext attributes() throws RecognitionException {
		AttributesContext _localctx = new AttributesContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_attributes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			match(OPEN_BRACE);
			setState(190);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 1729382256910270848L) != 0)) {
				{
				setState(182);
				attribute();
				setState(187);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(183);
					match(COMMA);
					setState(184);
					attribute();
					}
					}
					setState(189);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(192);
			match(CLOSE_BRACE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeContext extends ParserRuleContext {
		public ValueAttributeContext valueAttribute() {
			return getRuleContext(ValueAttributeContext.class,0);
		}
		public ConstraintAttributeContext constraintAttribute() {
			return getRuleContext(ConstraintAttributeContext.class,0);
		}
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_attribute);
		try {
			setState(196);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID_NOT_STRICT:
			case ID_STRICT:
				enterOuterAlt(_localctx, 1);
				{
				setState(194);
				valueAttribute();
				}
				break;
			case T__6:
			case T__7:
				enterOuterAlt(_localctx, 2);
				{
				setState(195);
				constraintAttribute();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ValueAttributeContext extends ParserRuleContext {
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ValueAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_valueAttribute; }
	}

	public final ValueAttributeContext valueAttribute() throws RecognitionException {
		ValueAttributeContext _localctx = new ValueAttributeContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_valueAttribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(198);
			key();
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2351371586696642560L) != 0)) {
				{
				setState(199);
				value();
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

	@SuppressWarnings("CheckReturnValue")
	public static class KeyContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public KeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_key; }
	}

	public final KeyContext key() throws RecognitionException {
		KeyContext _localctx = new KeyContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_key);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			id();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ValueContext extends ParserRuleContext {
		public TerminalNode BOOLEAN() { return getToken(UVLParser.BOOLEAN, 0); }
		public TerminalNode FLOAT() { return getToken(UVLParser.FLOAT, 0); }
		public TerminalNode INTEGER() { return getToken(UVLParser.INTEGER, 0); }
		public TerminalNode STRING() { return getToken(UVLParser.STRING, 0); }
		public AttributesContext attributes() {
			return getRuleContext(AttributesContext.class,0);
		}
		public VectorContext vector() {
			return getRuleContext(VectorContext.class,0);
		}
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_value);
		try {
			setState(210);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BOOLEAN:
				enterOuterAlt(_localctx, 1);
				{
				setState(204);
				match(BOOLEAN);
				}
				break;
			case FLOAT:
				enterOuterAlt(_localctx, 2);
				{
				setState(205);
				match(FLOAT);
				}
				break;
			case INTEGER:
				enterOuterAlt(_localctx, 3);
				{
				setState(206);
				match(INTEGER);
				}
				break;
			case STRING:
				enterOuterAlt(_localctx, 4);
				{
				setState(207);
				match(STRING);
				}
				break;
			case OPEN_BRACE:
				enterOuterAlt(_localctx, 5);
				{
				setState(208);
				attributes();
				}
				break;
			case OPEN_BRACK:
				enterOuterAlt(_localctx, 6);
				{
				setState(209);
				vector();
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

	@SuppressWarnings("CheckReturnValue")
	public static class VectorContext extends ParserRuleContext {
		public TerminalNode OPEN_BRACK() { return getToken(UVLParser.OPEN_BRACK, 0); }
		public TerminalNode CLOSE_BRACK() { return getToken(UVLParser.CLOSE_BRACK, 0); }
		public List<ValueContext> value() {
			return getRuleContexts(ValueContext.class);
		}
		public ValueContext value(int i) {
			return getRuleContext(ValueContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(UVLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(UVLParser.COMMA, i);
		}
		public VectorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector; }
	}

	public final VectorContext vector() throws RecognitionException {
		VectorContext _localctx = new VectorContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_vector);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(212);
			match(OPEN_BRACK);
			setState(221);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2351371586696642560L) != 0)) {
				{
				setState(213);
				value();
				setState(218);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(214);
					match(COMMA);
					setState(215);
					value();
					}
					}
					setState(220);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(223);
			match(CLOSE_BRACK);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ConstraintAttributeContext extends ParserRuleContext {
		public ConstraintAttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraintAttribute; }
	 
		public ConstraintAttributeContext() { }
		public void copyFrom(ConstraintAttributeContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ListConstraintAttributeContext extends ConstraintAttributeContext {
		public ConstraintListContext constraintList() {
			return getRuleContext(ConstraintListContext.class,0);
		}
		public ListConstraintAttributeContext(ConstraintAttributeContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SingleConstraintAttributeContext extends ConstraintAttributeContext {
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public SingleConstraintAttributeContext(ConstraintAttributeContext ctx) { copyFrom(ctx); }
	}

	public final ConstraintAttributeContext constraintAttribute() throws RecognitionException {
		ConstraintAttributeContext _localctx = new ConstraintAttributeContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_constraintAttribute);
		try {
			setState(229);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				_localctx = new SingleConstraintAttributeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(225);
				match(T__6);
				setState(226);
				constraint(0);
				}
				break;
			case T__7:
				_localctx = new ListConstraintAttributeContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(227);
				match(T__7);
				setState(228);
				constraintList();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ConstraintListContext extends ParserRuleContext {
		public TerminalNode OPEN_BRACK() { return getToken(UVLParser.OPEN_BRACK, 0); }
		public TerminalNode CLOSE_BRACK() { return getToken(UVLParser.CLOSE_BRACK, 0); }
		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}
		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(UVLParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(UVLParser.COMMA, i);
		}
		public ConstraintListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraintList; }
	}

	public final ConstraintListContext constraintList() throws RecognitionException {
		ConstraintListContext _localctx = new ConstraintListContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_constraintList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(231);
			match(OPEN_BRACK);
			setState(240);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4037688174317682176L) != 0)) {
				{
				setState(232);
				constraint(0);
				setState(237);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(233);
					match(COMMA);
					setState(234);
					constraint(0);
					}
					}
					setState(239);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(242);
			match(CLOSE_BRACK);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ConstraintsContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(UVLParser.NEWLINE, 0); }
		public TerminalNode INDENT() { return getToken(UVLParser.INDENT, 0); }
		public TerminalNode DEDENT() { return getToken(UVLParser.DEDENT, 0); }
		public List<ConstraintLineContext> constraintLine() {
			return getRuleContexts(ConstraintLineContext.class);
		}
		public ConstraintLineContext constraintLine(int i) {
			return getRuleContext(ConstraintLineContext.class,i);
		}
		public ConstraintsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraints; }
	}

	public final ConstraintsContext constraints() throws RecognitionException {
		ConstraintsContext _localctx = new ConstraintsContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_constraints);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(T__7);
			setState(245);
			match(NEWLINE);
			setState(246);
			match(INDENT);
			setState(250);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 4037688174317682176L) != 0)) {
				{
				{
				setState(247);
				constraintLine();
				}
				}
				setState(252);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(253);
			match(DEDENT);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ConstraintLineContext extends ParserRuleContext {
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public TerminalNode NEWLINE() { return getToken(UVLParser.NEWLINE, 0); }
		public ConstraintLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraintLine; }
	}

	public final ConstraintLineContext constraintLine() throws RecognitionException {
		ConstraintLineContext _localctx = new ConstraintLineContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_constraintLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(255);
			constraint(0);
			setState(256);
			match(NEWLINE);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ConstraintContext extends ParserRuleContext {
		public ConstraintContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_constraint; }
	 
		public ConstraintContext() { }
		public void copyFrom(ConstraintContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class OrConstraintContext extends ConstraintContext {
		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}
		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class,i);
		}
		public TerminalNode OR() { return getToken(UVLParser.OR, 0); }
		public OrConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EquationConstraintContext extends ConstraintContext {
		public EquationContext equation() {
			return getRuleContext(EquationContext.class,0);
		}
		public EquationConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LiteralConstraintContext extends ConstraintContext {
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public LiteralConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenthesisConstraintContext extends ConstraintContext {
		public TerminalNode OPEN_PAREN() { return getToken(UVLParser.OPEN_PAREN, 0); }
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public TerminalNode CLOSE_PAREN() { return getToken(UVLParser.CLOSE_PAREN, 0); }
		public ParenthesisConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotConstraintContext extends ConstraintContext {
		public TerminalNode NOT() { return getToken(UVLParser.NOT, 0); }
		public ConstraintContext constraint() {
			return getRuleContext(ConstraintContext.class,0);
		}
		public NotConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AndConstraintContext extends ConstraintContext {
		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}
		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class,i);
		}
		public TerminalNode AND() { return getToken(UVLParser.AND, 0); }
		public AndConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EquivalenceConstraintContext extends ConstraintContext {
		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}
		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class,i);
		}
		public TerminalNode EQUIVALENCE() { return getToken(UVLParser.EQUIVALENCE, 0); }
		public EquivalenceConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ImplicationConstraintContext extends ConstraintContext {
		public List<ConstraintContext> constraint() {
			return getRuleContexts(ConstraintContext.class);
		}
		public ConstraintContext constraint(int i) {
			return getRuleContext(ConstraintContext.class,i);
		}
		public TerminalNode IMPLICATION() { return getToken(UVLParser.IMPLICATION, 0); }
		public ImplicationConstraintContext(ConstraintContext ctx) { copyFrom(ctx); }
	}

	public final ConstraintContext constraint() throws RecognitionException {
		return constraint(0);
	}

	private ConstraintContext constraint(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ConstraintContext _localctx = new ConstraintContext(_ctx, _parentState);
		ConstraintContext _prevctx = _localctx;
		int _startState = 42;
		enterRecursionRule(_localctx, 42, RULE_constraint, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,30,_ctx) ) {
			case 1:
				{
				_localctx = new EquationConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(259);
				equation();
				}
				break;
			case 2:
				{
				_localctx = new LiteralConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(260);
				reference();
				}
				break;
			case 3:
				{
				_localctx = new ParenthesisConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(261);
				match(OPEN_PAREN);
				setState(262);
				constraint(0);
				setState(263);
				match(CLOSE_PAREN);
				}
				break;
			case 4:
				{
				_localctx = new NotConstraintContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(265);
				match(NOT);
				setState(266);
				constraint(5);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(283);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(281);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
					case 1:
						{
						_localctx = new AndConstraintContext(new ConstraintContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_constraint);
						setState(269);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(270);
						match(AND);
						setState(271);
						constraint(5);
						}
						break;
					case 2:
						{
						_localctx = new OrConstraintContext(new ConstraintContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_constraint);
						setState(272);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(273);
						match(OR);
						setState(274);
						constraint(4);
						}
						break;
					case 3:
						{
						_localctx = new ImplicationConstraintContext(new ConstraintContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_constraint);
						setState(275);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(276);
						match(IMPLICATION);
						setState(277);
						constraint(3);
						}
						break;
					case 4:
						{
						_localctx = new EquivalenceConstraintContext(new ConstraintContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_constraint);
						setState(278);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(279);
						match(EQUIVALENCE);
						setState(280);
						constraint(2);
						}
						break;
					}
					} 
				}
				setState(285);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class EquationContext extends ParserRuleContext {
		public EquationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_equation; }
	 
		public EquationContext() { }
		public void copyFrom(EquationContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EqualEquationContext extends EquationContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode EQUAL() { return getToken(UVLParser.EQUAL, 0); }
		public EqualEquationContext(EquationContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LowerEquationContext extends EquationContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LOWER() { return getToken(UVLParser.LOWER, 0); }
		public LowerEquationContext(EquationContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LowerEqualsEquationContext extends EquationContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LOWER_EQUALS() { return getToken(UVLParser.LOWER_EQUALS, 0); }
		public LowerEqualsEquationContext(EquationContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GreaterEqualsEquationContext extends EquationContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode GREATER_EQUALS() { return getToken(UVLParser.GREATER_EQUALS, 0); }
		public GreaterEqualsEquationContext(EquationContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class GreaterEquationContext extends EquationContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode GREATER() { return getToken(UVLParser.GREATER, 0); }
		public GreaterEquationContext(EquationContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotEqualsEquationContext extends EquationContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode NOT_EQUALS() { return getToken(UVLParser.NOT_EQUALS, 0); }
		public NotEqualsEquationContext(EquationContext ctx) { copyFrom(ctx); }
	}

	public final EquationContext equation() throws RecognitionException {
		EquationContext _localctx = new EquationContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_equation);
		try {
			setState(310);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,33,_ctx) ) {
			case 1:
				_localctx = new EqualEquationContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(286);
				expression(0);
				setState(287);
				match(EQUAL);
				setState(288);
				expression(0);
				}
				break;
			case 2:
				_localctx = new LowerEquationContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(290);
				expression(0);
				setState(291);
				match(LOWER);
				setState(292);
				expression(0);
				}
				break;
			case 3:
				_localctx = new GreaterEquationContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(294);
				expression(0);
				setState(295);
				match(GREATER);
				setState(296);
				expression(0);
				}
				break;
			case 4:
				_localctx = new LowerEqualsEquationContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(298);
				expression(0);
				setState(299);
				match(LOWER_EQUALS);
				setState(300);
				expression(0);
				}
				break;
			case 5:
				_localctx = new GreaterEqualsEquationContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(302);
				expression(0);
				setState(303);
				match(GREATER_EQUALS);
				setState(304);
				expression(0);
				}
				break;
			case 6:
				_localctx = new NotEqualsEquationContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(306);
				expression(0);
				setState(307);
				match(NOT_EQUALS);
				setState(308);
				expression(0);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BracketExpressionContext extends ExpressionContext {
		public TerminalNode OPEN_PAREN() { return getToken(UVLParser.OPEN_PAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode CLOSE_PAREN() { return getToken(UVLParser.CLOSE_PAREN, 0); }
		public BracketExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AggregateFunctionExpressionContext extends ExpressionContext {
		public AggregateFunctionContext aggregateFunction() {
			return getRuleContext(AggregateFunctionContext.class,0);
		}
		public AggregateFunctionExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FloatLiteralExpressionContext extends ExpressionContext {
		public TerminalNode FLOAT() { return getToken(UVLParser.FLOAT, 0); }
		public FloatLiteralExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringLiteralExpressionContext extends ExpressionContext {
		public TerminalNode STRING() { return getToken(UVLParser.STRING, 0); }
		public StringLiteralExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode ADD() { return getToken(UVLParser.ADD, 0); }
		public AddExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class IntegerLiteralExpressionContext extends ExpressionContext {
		public TerminalNode INTEGER() { return getToken(UVLParser.INTEGER, 0); }
		public IntegerLiteralExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LiteralExpressionContext extends ExpressionContext {
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public LiteralExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class DivExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode DIV() { return getToken(UVLParser.DIV, 0); }
		public DivExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SubExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode SUB() { return getToken(UVLParser.SUB, 0); }
		public SubExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MulExpressionContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode MUL() { return getToken(UVLParser.MUL, 0); }
		public MulExpressionContext(ExpressionContext ctx) { copyFrom(ctx); }
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 46;
		enterRecursionRule(_localctx, 46, RULE_expression, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(322);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FLOAT:
				{
				_localctx = new FloatLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(313);
				match(FLOAT);
				}
				break;
			case INTEGER:
				{
				_localctx = new IntegerLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(314);
				match(INTEGER);
				}
				break;
			case STRING:
				{
				_localctx = new StringLiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(315);
				match(STRING);
				}
				break;
			case T__8:
			case T__9:
			case T__10:
			case T__11:
			case T__12:
				{
				_localctx = new AggregateFunctionExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(316);
				aggregateFunction();
				}
				break;
			case ID_NOT_STRICT:
			case ID_STRICT:
				{
				_localctx = new LiteralExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(317);
				reference();
				}
				break;
			case OPEN_PAREN:
				{
				_localctx = new BracketExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(318);
				match(OPEN_PAREN);
				setState(319);
				expression(0);
				setState(320);
				match(CLOSE_PAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(338);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(336);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,35,_ctx) ) {
					case 1:
						{
						_localctx = new AddExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(324);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(325);
						match(ADD);
						setState(326);
						expression(5);
						}
						break;
					case 2:
						{
						_localctx = new SubExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(327);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(328);
						match(SUB);
						setState(329);
						expression(4);
						}
						break;
					case 3:
						{
						_localctx = new MulExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(330);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(331);
						match(MUL);
						setState(332);
						expression(3);
						}
						break;
					case 4:
						{
						_localctx = new DivExpressionContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(333);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(334);
						match(DIV);
						setState(335);
						expression(2);
						}
						break;
					}
					} 
				}
				setState(340);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,36,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class AggregateFunctionContext extends ParserRuleContext {
		public AggregateFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aggregateFunction; }
	 
		public AggregateFunctionContext() { }
		public void copyFrom(AggregateFunctionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AvgAggregateFunctionContext extends AggregateFunctionContext {
		public TerminalNode OPEN_PAREN() { return getToken(UVLParser.OPEN_PAREN, 0); }
		public List<ReferenceContext> reference() {
			return getRuleContexts(ReferenceContext.class);
		}
		public ReferenceContext reference(int i) {
			return getRuleContext(ReferenceContext.class,i);
		}
		public TerminalNode CLOSE_PAREN() { return getToken(UVLParser.CLOSE_PAREN, 0); }
		public TerminalNode COMMA() { return getToken(UVLParser.COMMA, 0); }
		public AvgAggregateFunctionContext(AggregateFunctionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumericAggregateFunctionExpressionContext extends AggregateFunctionContext {
		public NumericAggregateFunctionContext numericAggregateFunction() {
			return getRuleContext(NumericAggregateFunctionContext.class,0);
		}
		public NumericAggregateFunctionExpressionContext(AggregateFunctionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SumAggregateFunctionContext extends AggregateFunctionContext {
		public TerminalNode OPEN_PAREN() { return getToken(UVLParser.OPEN_PAREN, 0); }
		public List<ReferenceContext> reference() {
			return getRuleContexts(ReferenceContext.class);
		}
		public ReferenceContext reference(int i) {
			return getRuleContext(ReferenceContext.class,i);
		}
		public TerminalNode CLOSE_PAREN() { return getToken(UVLParser.CLOSE_PAREN, 0); }
		public TerminalNode COMMA() { return getToken(UVLParser.COMMA, 0); }
		public SumAggregateFunctionContext(AggregateFunctionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringAggregateFunctionExpressionContext extends AggregateFunctionContext {
		public StringAggregateFunctionContext stringAggregateFunction() {
			return getRuleContext(StringAggregateFunctionContext.class,0);
		}
		public StringAggregateFunctionExpressionContext(AggregateFunctionContext ctx) { copyFrom(ctx); }
	}

	public final AggregateFunctionContext aggregateFunction() throws RecognitionException {
		AggregateFunctionContext _localctx = new AggregateFunctionContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_aggregateFunction);
		try {
			setState(363);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
				_localctx = new SumAggregateFunctionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(341);
				match(T__8);
				setState(342);
				match(OPEN_PAREN);
				setState(346);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,37,_ctx) ) {
				case 1:
					{
					setState(343);
					reference();
					setState(344);
					match(COMMA);
					}
					break;
				}
				setState(348);
				reference();
				setState(349);
				match(CLOSE_PAREN);
				}
				break;
			case T__9:
				_localctx = new AvgAggregateFunctionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(351);
				match(T__9);
				setState(352);
				match(OPEN_PAREN);
				setState(356);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,38,_ctx) ) {
				case 1:
					{
					setState(353);
					reference();
					setState(354);
					match(COMMA);
					}
					break;
				}
				setState(358);
				reference();
				setState(359);
				match(CLOSE_PAREN);
				}
				break;
			case T__10:
				_localctx = new StringAggregateFunctionExpressionContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(361);
				stringAggregateFunction();
				}
				break;
			case T__11:
			case T__12:
				_localctx = new NumericAggregateFunctionExpressionContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(362);
				numericAggregateFunction();
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

	@SuppressWarnings("CheckReturnValue")
	public static class StringAggregateFunctionContext extends ParserRuleContext {
		public StringAggregateFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_stringAggregateFunction; }
	 
		public StringAggregateFunctionContext() { }
		public void copyFrom(StringAggregateFunctionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LengthAggregateFunctionContext extends StringAggregateFunctionContext {
		public TerminalNode OPEN_PAREN() { return getToken(UVLParser.OPEN_PAREN, 0); }
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public TerminalNode CLOSE_PAREN() { return getToken(UVLParser.CLOSE_PAREN, 0); }
		public LengthAggregateFunctionContext(StringAggregateFunctionContext ctx) { copyFrom(ctx); }
	}

	public final StringAggregateFunctionContext stringAggregateFunction() throws RecognitionException {
		StringAggregateFunctionContext _localctx = new StringAggregateFunctionContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_stringAggregateFunction);
		try {
			_localctx = new LengthAggregateFunctionContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(365);
			match(T__10);
			setState(366);
			match(OPEN_PAREN);
			setState(367);
			reference();
			setState(368);
			match(CLOSE_PAREN);
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

	@SuppressWarnings("CheckReturnValue")
	public static class NumericAggregateFunctionContext extends ParserRuleContext {
		public NumericAggregateFunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_numericAggregateFunction; }
	 
		public NumericAggregateFunctionContext() { }
		public void copyFrom(NumericAggregateFunctionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CeilAggregateFunctionContext extends NumericAggregateFunctionContext {
		public TerminalNode OPEN_PAREN() { return getToken(UVLParser.OPEN_PAREN, 0); }
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public TerminalNode CLOSE_PAREN() { return getToken(UVLParser.CLOSE_PAREN, 0); }
		public CeilAggregateFunctionContext(NumericAggregateFunctionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FloorAggregateFunctionContext extends NumericAggregateFunctionContext {
		public TerminalNode OPEN_PAREN() { return getToken(UVLParser.OPEN_PAREN, 0); }
		public ReferenceContext reference() {
			return getRuleContext(ReferenceContext.class,0);
		}
		public TerminalNode CLOSE_PAREN() { return getToken(UVLParser.CLOSE_PAREN, 0); }
		public FloorAggregateFunctionContext(NumericAggregateFunctionContext ctx) { copyFrom(ctx); }
	}

	public final NumericAggregateFunctionContext numericAggregateFunction() throws RecognitionException {
		NumericAggregateFunctionContext _localctx = new NumericAggregateFunctionContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_numericAggregateFunction);
		try {
			setState(380);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__11:
				_localctx = new FloorAggregateFunctionContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(370);
				match(T__11);
				setState(371);
				match(OPEN_PAREN);
				setState(372);
				reference();
				setState(373);
				match(CLOSE_PAREN);
				}
				break;
			case T__12:
				_localctx = new CeilAggregateFunctionContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(375);
				match(T__12);
				setState(376);
				match(OPEN_PAREN);
				setState(377);
				reference();
				setState(378);
				match(CLOSE_PAREN);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ReferenceContext extends ParserRuleContext {
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public ReferenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_reference; }
	}

	public final ReferenceContext reference() throws RecognitionException {
		ReferenceContext _localctx = new ReferenceContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_reference);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(387);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(382);
					id();
					setState(383);
					match(T__13);
					}
					} 
				}
				setState(389);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,41,_ctx);
			}
			setState(390);
			id();
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

	@SuppressWarnings("CheckReturnValue")
	public static class IdContext extends ParserRuleContext {
		public TerminalNode ID_STRICT() { return getToken(UVLParser.ID_STRICT, 0); }
		public TerminalNode ID_NOT_STRICT() { return getToken(UVLParser.ID_NOT_STRICT, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(392);
			_la = _input.LA(1);
			if ( !(_la==ID_NOT_STRICT || _la==ID_STRICT) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class FeatureTypeContext extends ParserRuleContext {
		public TerminalNode BOOLEAN_KEY() { return getToken(UVLParser.BOOLEAN_KEY, 0); }
		public FeatureTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_featureType; }
	}

	public final FeatureTypeContext featureType() throws RecognitionException {
		FeatureTypeContext _localctx = new FeatureTypeContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_featureType);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(394);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 562949953650688L) != 0)) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class LanguageLevelContext extends ParserRuleContext {
		public MajorLevelContext majorLevel() {
			return getRuleContext(MajorLevelContext.class,0);
		}
		public MinorLevelContext minorLevel() {
			return getRuleContext(MinorLevelContext.class,0);
		}
		public TerminalNode MUL() { return getToken(UVLParser.MUL, 0); }
		public LanguageLevelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_languageLevel; }
	}

	public final LanguageLevelContext languageLevel() throws RecognitionException {
		LanguageLevelContext _localctx = new LanguageLevelContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_languageLevel);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(396);
			majorLevel();
			setState(402);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__13) {
				{
				setState(397);
				match(T__13);
				setState(400);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case T__19:
				case T__20:
				case T__21:
				case T__22:
					{
					setState(398);
					minorLevel();
					}
					break;
				case MUL:
					{
					setState(399);
					match(MUL);
					}
					break;
				default:
					throw new NoViableAltException(this);
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

	@SuppressWarnings("CheckReturnValue")
	public static class MajorLevelContext extends ParserRuleContext {
		public TerminalNode BOOLEAN_KEY() { return getToken(UVLParser.BOOLEAN_KEY, 0); }
		public MajorLevelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_majorLevel; }
	}

	public final MajorLevelContext majorLevel() throws RecognitionException {
		MajorLevelContext _localctx = new MajorLevelContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_majorLevel);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(404);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 562949954207744L) != 0)) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class MinorLevelContext extends ParserRuleContext {
		public MinorLevelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_minorLevel; }
	}

	public final MinorLevelContext minorLevel() throws RecognitionException {
		MinorLevelContext _localctx = new MinorLevelContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_minorLevel);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(406);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 15728640L) != 0)) ) {
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
		case 21:
			return constraint_sempred((ConstraintContext)_localctx, predIndex);
		case 23:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean constraint_sempred(ConstraintContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		case 2:
			return precpred(_ctx, 2);
		case 3:
			return precpred(_ctx, 1);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 4:
			return precpred(_ctx, 4);
		case 5:
			return precpred(_ctx, 3);
		case 6:
			return precpred(_ctx, 2);
		case 7:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001?\u0199\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007\u0018"+
		"\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007\u001b"+
		"\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007\u001e"+
		"\u0002\u001f\u0007\u001f\u0002 \u0007 \u0001\u0000\u0003\u0000D\b\u0000"+
		"\u0001\u0000\u0003\u0000G\b\u0000\u0001\u0000\u0003\u0000J\b\u0000\u0001"+
		"\u0000\u0003\u0000M\b\u0000\u0001\u0000\u0003\u0000P\b\u0000\u0001\u0000"+
		"\u0003\u0000S\b\u0000\u0001\u0000\u0003\u0000V\b\u0000\u0001\u0000\u0003"+
		"\u0000Y\b\u0000\u0001\u0000\u0003\u0000\\\b\u0000\u0001\u0000\u0001\u0000"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001d\b\u0001"+
		"\n\u0001\f\u0001g\t\u0001\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0005\u0004u\b\u0004\n\u0004\f\u0004x\t\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005"+
		"\u007f\b\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0003\u0007\u0093\b\u0007\u0001\b\u0001\b\u0001\b\u0004\b"+
		"\u0098\b\b\u000b\b\f\b\u0099\u0001\b\u0001\b\u0001\t\u0003\t\u009f\b\t"+
		"\u0001\t\u0001\t\u0003\t\u00a3\b\t\u0001\t\u0003\t\u00a6\b\t\u0001\t\u0001"+
		"\t\u0001\t\u0004\t\u00ab\b\t\u000b\t\f\t\u00ac\u0001\t\u0001\t\u0003\t"+
		"\u00b1\b\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0005\u000b\u00ba\b\u000b\n\u000b\f\u000b\u00bd\t\u000b\u0003"+
		"\u000b\u00bf\b\u000b\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0003\f\u00c5"+
		"\b\f\u0001\r\u0001\r\u0003\r\u00c9\b\r\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f"+
		"\u00d3\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010"+
		"\u00d9\b\u0010\n\u0010\f\u0010\u00dc\t\u0010\u0003\u0010\u00de\b\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0003\u0011\u00e6\b\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012"+
		"\u0005\u0012\u00ec\b\u0012\n\u0012\f\u0012\u00ef\t\u0012\u0003\u0012\u00f1"+
		"\b\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0005\u0013\u00f9\b\u0013\n\u0013\f\u0013\u00fc\t\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0003\u0015\u010c\b\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015"+
		"\u0001\u0015\u0001\u0015\u0001\u0015\u0005\u0015\u011a\b\u0015\n\u0015"+
		"\f\u0015\u011d\t\u0015\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0003\u0016\u0137\b\u0016\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0003\u0017\u0143\b\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0017\u0001\u0017\u0001\u0017\u0005\u0017\u0151\b\u0017"+
		"\n\u0017\f\u0017\u0154\t\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0003\u0018\u015b\b\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0003"+
		"\u0018\u0165\b\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001"+
		"\u0018\u0003\u0018\u016c\b\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001"+
		"\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001"+
		"\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001a\u0003"+
		"\u001a\u017d\b\u001a\u0001\u001b\u0001\u001b\u0001\u001b\u0005\u001b\u0182"+
		"\b\u001b\n\u001b\f\u001b\u0185\t\u001b\u0001\u001b\u0001\u001b\u0001\u001c"+
		"\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0003\u001e\u0191\b\u001e\u0003\u001e\u0193\b\u001e\u0001"+
		"\u001f\u0001\u001f\u0001 \u0001 \u0001 \u0000\u0002*.!\u0000\u0002\u0004"+
		"\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \""+
		"$&(*,.02468:<>@\u0000\u0004\u0001\u0000;<\u0002\u0000\u000f\u001111\u0002"+
		"\u0000\u0012\u001311\u0001\u0000\u0014\u0017\u01ba\u0000C\u0001\u0000"+
		"\u0000\u0000\u0002_\u0001\u0000\u0000\u0000\u0004j\u0001\u0000\u0000\u0000"+
		"\u0006m\u0001\u0000\u0000\u0000\bp\u0001\u0000\u0000\u0000\n{\u0001\u0000"+
		"\u0000\u0000\f\u0082\u0001\u0000\u0000\u0000\u000e\u0092\u0001\u0000\u0000"+
		"\u0000\u0010\u0094\u0001\u0000\u0000\u0000\u0012\u009e\u0001\u0000\u0000"+
		"\u0000\u0014\u00b2\u0001\u0000\u0000\u0000\u0016\u00b5\u0001\u0000\u0000"+
		"\u0000\u0018\u00c4\u0001\u0000\u0000\u0000\u001a\u00c6\u0001\u0000\u0000"+
		"\u0000\u001c\u00ca\u0001\u0000\u0000\u0000\u001e\u00d2\u0001\u0000\u0000"+
		"\u0000 \u00d4\u0001\u0000\u0000\u0000\"\u00e5\u0001\u0000\u0000\u0000"+
		"$\u00e7\u0001\u0000\u0000\u0000&\u00f4\u0001\u0000\u0000\u0000(\u00ff"+
		"\u0001\u0000\u0000\u0000*\u010b\u0001\u0000\u0000\u0000,\u0136\u0001\u0000"+
		"\u0000\u0000.\u0142\u0001\u0000\u0000\u00000\u016b\u0001\u0000\u0000\u0000"+
		"2\u016d\u0001\u0000\u0000\u00004\u017c\u0001\u0000\u0000\u00006\u0183"+
		"\u0001\u0000\u0000\u00008\u0188\u0001\u0000\u0000\u0000:\u018a\u0001\u0000"+
		"\u0000\u0000<\u018c\u0001\u0000\u0000\u0000>\u0194\u0001\u0000\u0000\u0000"+
		"@\u0196\u0001\u0000\u0000\u0000BD\u0003\u0006\u0003\u0000CB\u0001\u0000"+
		"\u0000\u0000CD\u0001\u0000\u0000\u0000DF\u0001\u0000\u0000\u0000EG\u0005"+
		">\u0000\u0000FE\u0001\u0000\u0000\u0000FG\u0001\u0000\u0000\u0000GI\u0001"+
		"\u0000\u0000\u0000HJ\u0003\u0002\u0001\u0000IH\u0001\u0000\u0000\u0000"+
		"IJ\u0001\u0000\u0000\u0000JL\u0001\u0000\u0000\u0000KM\u0005>\u0000\u0000"+
		"LK\u0001\u0000\u0000\u0000LM\u0001\u0000\u0000\u0000MO\u0001\u0000\u0000"+
		"\u0000NP\u0003\b\u0004\u0000ON\u0001\u0000\u0000\u0000OP\u0001\u0000\u0000"+
		"\u0000PR\u0001\u0000\u0000\u0000QS\u0005>\u0000\u0000RQ\u0001\u0000\u0000"+
		"\u0000RS\u0001\u0000\u0000\u0000SU\u0001\u0000\u0000\u0000TV\u0003\f\u0006"+
		"\u0000UT\u0001\u0000\u0000\u0000UV\u0001\u0000\u0000\u0000VX\u0001\u0000"+
		"\u0000\u0000WY\u0005>\u0000\u0000XW\u0001\u0000\u0000\u0000XY\u0001\u0000"+
		"\u0000\u0000Y[\u0001\u0000\u0000\u0000Z\\\u0003&\u0013\u0000[Z\u0001\u0000"+
		"\u0000\u0000[\\\u0001\u0000\u0000\u0000\\]\u0001\u0000\u0000\u0000]^\u0005"+
		"\u0000\u0000\u0001^\u0001\u0001\u0000\u0000\u0000_`\u0005\u0001\u0000"+
		"\u0000`a\u0005>\u0000\u0000ae\u0005\u0018\u0000\u0000bd\u0003\u0004\u0002"+
		"\u0000cb\u0001\u0000\u0000\u0000dg\u0001\u0000\u0000\u0000ec\u0001\u0000"+
		"\u0000\u0000ef\u0001\u0000\u0000\u0000fh\u0001\u0000\u0000\u0000ge\u0001"+
		"\u0000\u0000\u0000hi\u0005\u0019\u0000\u0000i\u0003\u0001\u0000\u0000"+
		"\u0000jk\u0003<\u001e\u0000kl\u0005>\u0000\u0000l\u0005\u0001\u0000\u0000"+
		"\u0000mn\u0005\u0002\u0000\u0000no\u00036\u001b\u0000o\u0007\u0001\u0000"+
		"\u0000\u0000pq\u0005\u0003\u0000\u0000qr\u0005>\u0000\u0000rv\u0005\u0018"+
		"\u0000\u0000su\u0003\n\u0005\u0000ts\u0001\u0000\u0000\u0000ux\u0001\u0000"+
		"\u0000\u0000vt\u0001\u0000\u0000\u0000vw\u0001\u0000\u0000\u0000wy\u0001"+
		"\u0000\u0000\u0000xv\u0001\u0000\u0000\u0000yz\u0005\u0019\u0000\u0000"+
		"z\t\u0001\u0000\u0000\u0000{~\u00036\u001b\u0000|}\u0005\u0004\u0000\u0000"+
		"}\u007f\u00036\u001b\u0000~|\u0001\u0000\u0000\u0000~\u007f\u0001\u0000"+
		"\u0000\u0000\u007f\u0080\u0001\u0000\u0000\u0000\u0080\u0081\u0005>\u0000"+
		"\u0000\u0081\u000b\u0001\u0000\u0000\u0000\u0082\u0083\u0005\u0005\u0000"+
		"\u0000\u0083\u0084\u0005>\u0000\u0000\u0084\u0085\u0005\u0018\u0000\u0000"+
		"\u0085\u0086\u0003\u0012\t\u0000\u0086\u0087\u0005\u0019\u0000\u0000\u0087"+
		"\r\u0001\u0000\u0000\u0000\u0088\u0089\u0005\u001a\u0000\u0000\u0089\u0093"+
		"\u0003\u0010\b\u0000\u008a\u008b\u0005\u001b\u0000\u0000\u008b\u0093\u0003"+
		"\u0010\b\u0000\u008c\u008d\u0005\u001c\u0000\u0000\u008d\u0093\u0003\u0010"+
		"\b\u0000\u008e\u008f\u0005\u001d\u0000\u0000\u008f\u0093\u0003\u0010\b"+
		"\u0000\u0090\u0091\u0005\u001e\u0000\u0000\u0091\u0093\u0003\u0010\b\u0000"+
		"\u0092\u0088\u0001\u0000\u0000\u0000\u0092\u008a\u0001\u0000\u0000\u0000"+
		"\u0092\u008c\u0001\u0000\u0000\u0000\u0092\u008e\u0001\u0000\u0000\u0000"+
		"\u0092\u0090\u0001\u0000\u0000\u0000\u0093\u000f\u0001\u0000\u0000\u0000"+
		"\u0094\u0095\u0005>\u0000\u0000\u0095\u0097\u0005\u0018\u0000\u0000\u0096"+
		"\u0098\u0003\u0012\t\u0000\u0097\u0096\u0001\u0000\u0000\u0000\u0098\u0099"+
		"\u0001\u0000\u0000\u0000\u0099\u0097\u0001\u0000\u0000\u0000\u0099\u009a"+
		"\u0001\u0000\u0000\u0000\u009a\u009b\u0001\u0000\u0000\u0000\u009b\u009c"+
		"\u0005\u0019\u0000\u0000\u009c\u0011\u0001\u0000\u0000\u0000\u009d\u009f"+
		"\u0003:\u001d\u0000\u009e\u009d\u0001\u0000\u0000\u0000\u009e\u009f\u0001"+
		"\u0000\u0000\u0000\u009f\u00a0\u0001\u0000\u0000\u0000\u00a0\u00a2\u0003"+
		"6\u001b\u0000\u00a1\u00a3\u0003\u0014\n\u0000\u00a2\u00a1\u0001\u0000"+
		"\u0000\u0000\u00a2\u00a3\u0001\u0000\u0000\u0000\u00a3\u00a5\u0001\u0000"+
		"\u0000\u0000\u00a4\u00a6\u0003\u0016\u000b\u0000\u00a5\u00a4\u0001\u0000"+
		"\u0000\u0000\u00a5\u00a6\u0001\u0000\u0000\u0000\u00a6\u00a7\u0001\u0000"+
		"\u0000\u0000\u00a7\u00b0\u0005>\u0000\u0000\u00a8\u00aa\u0005\u0018\u0000"+
		"\u0000\u00a9\u00ab\u0003\u000e\u0007\u0000\u00aa\u00a9\u0001\u0000\u0000"+
		"\u0000\u00ab\u00ac\u0001\u0000\u0000\u0000\u00ac\u00aa\u0001\u0000\u0000"+
		"\u0000\u00ac\u00ad\u0001\u0000\u0000\u0000\u00ad\u00ae\u0001\u0000\u0000"+
		"\u0000\u00ae\u00af\u0005\u0019\u0000\u0000\u00af\u00b1\u0001\u0000\u0000"+
		"\u0000\u00b0\u00a8\u0001\u0000\u0000\u0000\u00b0\u00b1\u0001\u0000\u0000"+
		"\u0000\u00b1\u0013\u0001\u0000\u0000\u0000\u00b2\u00b3\u0005\u0006\u0000"+
		"\u0000\u00b3\u00b4\u0005\u001e\u0000\u0000\u00b4\u0015\u0001\u0000\u0000"+
		"\u0000\u00b5\u00be\u00057\u0000\u0000\u00b6\u00bb\u0003\u0018\f\u0000"+
		"\u00b7\u00b8\u00052\u0000\u0000\u00b8\u00ba\u0003\u0018\f\u0000\u00b9"+
		"\u00b7\u0001\u0000\u0000\u0000\u00ba\u00bd\u0001\u0000\u0000\u0000\u00bb"+
		"\u00b9\u0001\u0000\u0000\u0000\u00bb\u00bc\u0001\u0000\u0000\u0000\u00bc"+
		"\u00bf\u0001\u0000\u0000\u0000\u00bd\u00bb\u0001\u0000\u0000\u0000\u00be"+
		"\u00b6\u0001\u0000\u0000\u0000\u00be\u00bf\u0001\u0000\u0000\u0000\u00bf"+
		"\u00c0\u0001\u0000\u0000\u0000\u00c0\u00c1\u00058\u0000\u0000\u00c1\u0017"+
		"\u0001\u0000\u0000\u0000\u00c2\u00c5\u0003\u001a\r\u0000\u00c3\u00c5\u0003"+
		"\"\u0011\u0000\u00c4\u00c2\u0001\u0000\u0000\u0000\u00c4\u00c3\u0001\u0000"+
		"\u0000\u0000\u00c5\u0019\u0001\u0000\u0000\u0000\u00c6\u00c8\u0003\u001c"+
		"\u000e\u0000\u00c7\u00c9\u0003\u001e\u000f\u0000\u00c8\u00c7\u0001\u0000"+
		"\u0000\u0000\u00c8\u00c9\u0001\u0000\u0000\u0000\u00c9\u001b\u0001\u0000"+
		"\u0000\u0000\u00ca\u00cb\u00038\u001c\u0000\u00cb\u001d\u0001\u0000\u0000"+
		"\u0000\u00cc\u00d3\u00050\u0000\u0000\u00cd\u00d3\u0005.\u0000\u0000\u00ce"+
		"\u00d3\u0005/\u0000\u0000\u00cf\u00d3\u0005=\u0000\u0000\u00d0\u00d3\u0003"+
		"\u0016\u000b\u0000\u00d1\u00d3\u0003 \u0010\u0000\u00d2\u00cc\u0001\u0000"+
		"\u0000\u0000\u00d2\u00cd\u0001\u0000\u0000\u0000\u00d2\u00ce\u0001\u0000"+
		"\u0000\u0000\u00d2\u00cf\u0001\u0000\u0000\u0000\u00d2\u00d0\u0001\u0000"+
		"\u0000\u0000\u00d2\u00d1\u0001\u0000\u0000\u0000\u00d3\u001f\u0001\u0000"+
		"\u0000\u0000\u00d4\u00dd\u00055\u0000\u0000\u00d5\u00da\u0003\u001e\u000f"+
		"\u0000\u00d6\u00d7\u00052\u0000\u0000\u00d7\u00d9\u0003\u001e\u000f\u0000"+
		"\u00d8\u00d6\u0001\u0000\u0000\u0000\u00d9\u00dc\u0001\u0000\u0000\u0000"+
		"\u00da\u00d8\u0001\u0000\u0000\u0000\u00da\u00db\u0001\u0000\u0000\u0000"+
		"\u00db\u00de\u0001\u0000\u0000\u0000\u00dc\u00da\u0001\u0000\u0000\u0000"+
		"\u00dd\u00d5\u0001\u0000\u0000\u0000\u00dd\u00de\u0001\u0000\u0000\u0000"+
		"\u00de\u00df\u0001\u0000\u0000\u0000\u00df\u00e0\u00056\u0000\u0000\u00e0"+
		"!\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005\u0007\u0000\u0000\u00e2\u00e6"+
		"\u0003*\u0015\u0000\u00e3\u00e4\u0005\b\u0000\u0000\u00e4\u00e6\u0003"+
		"$\u0012\u0000\u00e5\u00e1\u0001\u0000\u0000\u0000\u00e5\u00e3\u0001\u0000"+
		"\u0000\u0000\u00e6#\u0001\u0000\u0000\u0000\u00e7\u00f0\u00055\u0000\u0000"+
		"\u00e8\u00ed\u0003*\u0015\u0000\u00e9\u00ea\u00052\u0000\u0000\u00ea\u00ec"+
		"\u0003*\u0015\u0000\u00eb\u00e9\u0001\u0000\u0000\u0000\u00ec\u00ef\u0001"+
		"\u0000\u0000\u0000\u00ed\u00eb\u0001\u0000\u0000\u0000\u00ed\u00ee\u0001"+
		"\u0000\u0000\u0000\u00ee\u00f1\u0001\u0000\u0000\u0000\u00ef\u00ed\u0001"+
		"\u0000\u0000\u0000\u00f0\u00e8\u0001\u0000\u0000\u0000\u00f0\u00f1\u0001"+
		"\u0000\u0000\u0000\u00f1\u00f2\u0001\u0000\u0000\u0000\u00f2\u00f3\u0005"+
		"6\u0000\u0000\u00f3%\u0001\u0000\u0000\u0000\u00f4\u00f5\u0005\b\u0000"+
		"\u0000\u00f5\u00f6\u0005>\u0000\u0000\u00f6\u00fa\u0005\u0018\u0000\u0000"+
		"\u00f7\u00f9\u0003(\u0014\u0000\u00f8\u00f7\u0001\u0000\u0000\u0000\u00f9"+
		"\u00fc\u0001\u0000\u0000\u0000\u00fa\u00f8\u0001\u0000\u0000\u0000\u00fa"+
		"\u00fb\u0001\u0000\u0000\u0000\u00fb\u00fd\u0001\u0000\u0000\u0000\u00fc"+
		"\u00fa\u0001\u0000\u0000\u0000\u00fd\u00fe\u0005\u0019\u0000\u0000\u00fe"+
		"\'\u0001\u0000\u0000\u0000\u00ff\u0100\u0003*\u0015\u0000\u0100\u0101"+
		"\u0005>\u0000\u0000\u0101)\u0001\u0000\u0000\u0000\u0102\u0103\u0006\u0015"+
		"\uffff\uffff\u0000\u0103\u010c\u0003,\u0016\u0000\u0104\u010c\u00036\u001b"+
		"\u0000\u0105\u0106\u00053\u0000\u0000\u0106\u0107\u0003*\u0015\u0000\u0107"+
		"\u0108\u00054\u0000\u0000\u0108\u010c\u0001\u0000\u0000\u0000\u0109\u010a"+
		"\u0005\u001f\u0000\u0000\u010a\u010c\u0003*\u0015\u0005\u010b\u0102\u0001"+
		"\u0000\u0000\u0000\u010b\u0104\u0001\u0000\u0000\u0000\u010b\u0105\u0001"+
		"\u0000\u0000\u0000\u010b\u0109\u0001\u0000\u0000\u0000\u010c\u011b\u0001"+
		"\u0000\u0000\u0000\u010d\u010e\n\u0004\u0000\u0000\u010e\u010f\u0005 "+
		"\u0000\u0000\u010f\u011a\u0003*\u0015\u0005\u0110\u0111\n\u0003\u0000"+
		"\u0000\u0111\u0112\u0005!\u0000\u0000\u0112\u011a\u0003*\u0015\u0004\u0113"+
		"\u0114\n\u0002\u0000\u0000\u0114\u0115\u0005#\u0000\u0000\u0115\u011a"+
		"\u0003*\u0015\u0003\u0116\u0117\n\u0001\u0000\u0000\u0117\u0118\u0005"+
		"\"\u0000\u0000\u0118\u011a\u0003*\u0015\u0002\u0119\u010d\u0001\u0000"+
		"\u0000\u0000\u0119\u0110\u0001\u0000\u0000\u0000\u0119\u0113\u0001\u0000"+
		"\u0000\u0000\u0119\u0116\u0001\u0000\u0000\u0000\u011a\u011d\u0001\u0000"+
		"\u0000\u0000\u011b\u0119\u0001\u0000\u0000\u0000\u011b\u011c\u0001\u0000"+
		"\u0000\u0000\u011c+\u0001\u0000\u0000\u0000\u011d\u011b\u0001\u0000\u0000"+
		"\u0000\u011e\u011f\u0003.\u0017\u0000\u011f\u0120\u0005$\u0000\u0000\u0120"+
		"\u0121\u0003.\u0017\u0000\u0121\u0137\u0001\u0000\u0000\u0000\u0122\u0123"+
		"\u0003.\u0017\u0000\u0123\u0124\u0005%\u0000\u0000\u0124\u0125\u0003."+
		"\u0017\u0000\u0125\u0137\u0001\u0000\u0000\u0000\u0126\u0127\u0003.\u0017"+
		"\u0000\u0127\u0128\u0005\'\u0000\u0000\u0128\u0129\u0003.\u0017\u0000"+
		"\u0129\u0137\u0001\u0000\u0000\u0000\u012a\u012b\u0003.\u0017\u0000\u012b"+
		"\u012c\u0005&\u0000\u0000\u012c\u012d\u0003.\u0017\u0000\u012d\u0137\u0001"+
		"\u0000\u0000\u0000\u012e\u012f\u0003.\u0017\u0000\u012f\u0130\u0005(\u0000"+
		"\u0000\u0130\u0131\u0003.\u0017\u0000\u0131\u0137\u0001\u0000\u0000\u0000"+
		"\u0132\u0133\u0003.\u0017\u0000\u0133\u0134\u0005)\u0000\u0000\u0134\u0135"+
		"\u0003.\u0017\u0000\u0135\u0137\u0001\u0000\u0000\u0000\u0136\u011e\u0001"+
		"\u0000\u0000\u0000\u0136\u0122\u0001\u0000\u0000\u0000\u0136\u0126\u0001"+
		"\u0000\u0000\u0000\u0136\u012a\u0001\u0000\u0000\u0000\u0136\u012e\u0001"+
		"\u0000\u0000\u0000\u0136\u0132\u0001\u0000\u0000\u0000\u0137-\u0001\u0000"+
		"\u0000\u0000\u0138\u0139\u0006\u0017\uffff\uffff\u0000\u0139\u0143\u0005"+
		".\u0000\u0000\u013a\u0143\u0005/\u0000\u0000\u013b\u0143\u0005=\u0000"+
		"\u0000\u013c\u0143\u00030\u0018\u0000\u013d\u0143\u00036\u001b\u0000\u013e"+
		"\u013f\u00053\u0000\u0000\u013f\u0140\u0003.\u0017\u0000\u0140\u0141\u0005"+
		"4\u0000\u0000\u0141\u0143\u0001\u0000\u0000\u0000\u0142\u0138\u0001\u0000"+
		"\u0000\u0000\u0142\u013a\u0001\u0000\u0000\u0000\u0142\u013b\u0001\u0000"+
		"\u0000\u0000\u0142\u013c\u0001\u0000\u0000\u0000\u0142\u013d\u0001\u0000"+
		"\u0000\u0000\u0142\u013e\u0001\u0000\u0000\u0000\u0143\u0152\u0001\u0000"+
		"\u0000\u0000\u0144\u0145\n\u0004\u0000\u0000\u0145\u0146\u0005,\u0000"+
		"\u0000\u0146\u0151\u0003.\u0017\u0005\u0147\u0148\n\u0003\u0000\u0000"+
		"\u0148\u0149\u0005-\u0000\u0000\u0149\u0151\u0003.\u0017\u0004\u014a\u014b"+
		"\n\u0002\u0000\u0000\u014b\u014c\u0005+\u0000\u0000\u014c\u0151\u0003"+
		".\u0017\u0003\u014d\u014e\n\u0001\u0000\u0000\u014e\u014f\u0005*\u0000"+
		"\u0000\u014f\u0151\u0003.\u0017\u0002\u0150\u0144\u0001\u0000\u0000\u0000"+
		"\u0150\u0147\u0001\u0000\u0000\u0000\u0150\u014a\u0001\u0000\u0000\u0000"+
		"\u0150\u014d\u0001\u0000\u0000\u0000\u0151\u0154\u0001\u0000\u0000\u0000"+
		"\u0152\u0150\u0001\u0000\u0000\u0000\u0152\u0153\u0001\u0000\u0000\u0000"+
		"\u0153/\u0001\u0000\u0000\u0000\u0154\u0152\u0001\u0000\u0000\u0000\u0155"+
		"\u0156\u0005\t\u0000\u0000\u0156\u015a\u00053\u0000\u0000\u0157\u0158"+
		"\u00036\u001b\u0000\u0158\u0159\u00052\u0000\u0000\u0159\u015b\u0001\u0000"+
		"\u0000\u0000\u015a\u0157\u0001\u0000\u0000\u0000\u015a\u015b\u0001\u0000"+
		"\u0000\u0000\u015b\u015c\u0001\u0000\u0000\u0000\u015c\u015d\u00036\u001b"+
		"\u0000\u015d\u015e\u00054\u0000\u0000\u015e\u016c\u0001\u0000\u0000\u0000"+
		"\u015f\u0160\u0005\n\u0000\u0000\u0160\u0164\u00053\u0000\u0000\u0161"+
		"\u0162\u00036\u001b\u0000\u0162\u0163\u00052\u0000\u0000\u0163\u0165\u0001"+
		"\u0000\u0000\u0000\u0164\u0161\u0001\u0000\u0000\u0000\u0164\u0165\u0001"+
		"\u0000\u0000\u0000\u0165\u0166\u0001\u0000\u0000\u0000\u0166\u0167\u0003"+
		"6\u001b\u0000\u0167\u0168\u00054\u0000\u0000\u0168\u016c\u0001\u0000\u0000"+
		"\u0000\u0169\u016c\u00032\u0019\u0000\u016a\u016c\u00034\u001a\u0000\u016b"+
		"\u0155\u0001\u0000\u0000\u0000\u016b\u015f\u0001\u0000\u0000\u0000\u016b"+
		"\u0169\u0001\u0000\u0000\u0000\u016b\u016a\u0001\u0000\u0000\u0000\u016c"+
		"1\u0001\u0000\u0000\u0000\u016d\u016e\u0005\u000b\u0000\u0000\u016e\u016f"+
		"\u00053\u0000\u0000\u016f\u0170\u00036\u001b\u0000\u0170\u0171\u00054"+
		"\u0000\u0000\u01713\u0001\u0000\u0000\u0000\u0172\u0173\u0005\f\u0000"+
		"\u0000\u0173\u0174\u00053\u0000\u0000\u0174\u0175\u00036\u001b\u0000\u0175"+
		"\u0176\u00054\u0000\u0000\u0176\u017d\u0001\u0000\u0000\u0000\u0177\u0178"+
		"\u0005\r\u0000\u0000\u0178\u0179\u00053\u0000\u0000\u0179\u017a\u0003"+
		"6\u001b\u0000\u017a\u017b\u00054\u0000\u0000\u017b\u017d\u0001\u0000\u0000"+
		"\u0000\u017c\u0172\u0001\u0000\u0000\u0000\u017c\u0177\u0001\u0000\u0000"+
		"\u0000\u017d5\u0001\u0000\u0000\u0000\u017e\u017f\u00038\u001c\u0000\u017f"+
		"\u0180\u0005\u000e\u0000\u0000\u0180\u0182\u0001\u0000\u0000\u0000\u0181"+
		"\u017e\u0001\u0000\u0000\u0000\u0182\u0185\u0001\u0000\u0000\u0000\u0183"+
		"\u0181\u0001\u0000\u0000\u0000\u0183\u0184\u0001\u0000\u0000\u0000\u0184"+
		"\u0186\u0001\u0000\u0000\u0000\u0185\u0183\u0001\u0000\u0000\u0000\u0186"+
		"\u0187\u00038\u001c\u0000\u01877\u0001\u0000\u0000\u0000\u0188\u0189\u0007"+
		"\u0000\u0000\u0000\u01899\u0001\u0000\u0000\u0000\u018a\u018b\u0007\u0001"+
		"\u0000\u0000\u018b;\u0001\u0000\u0000\u0000\u018c\u0192\u0003>\u001f\u0000"+
		"\u018d\u0190\u0005\u000e\u0000\u0000\u018e\u0191\u0003@ \u0000\u018f\u0191"+
		"\u0005+\u0000\u0000\u0190\u018e\u0001\u0000\u0000\u0000\u0190\u018f\u0001"+
		"\u0000\u0000\u0000\u0191\u0193\u0001\u0000\u0000\u0000\u0192\u018d\u0001"+
		"\u0000\u0000\u0000\u0192\u0193\u0001\u0000\u0000\u0000\u0193=\u0001\u0000"+
		"\u0000\u0000\u0194\u0195\u0007\u0002\u0000\u0000\u0195?\u0001\u0000\u0000"+
		"\u0000\u0196\u0197\u0007\u0003\u0000\u0000\u0197A\u0001\u0000\u0000\u0000"+
		",CFILORUX[ev~\u0092\u0099\u009e\u00a2\u00a5\u00ac\u00b0\u00bb\u00be\u00c4"+
		"\u00c8\u00d2\u00da\u00dd\u00e5\u00ed\u00f0\u00fa\u010b\u0119\u011b\u0136"+
		"\u0142\u0150\u0152\u015a\u0164\u016b\u017c\u0183\u0190\u0192";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}