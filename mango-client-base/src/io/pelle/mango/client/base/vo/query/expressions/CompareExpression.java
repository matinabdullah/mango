package io.pelle.mango.client.base.vo.query.expressions;

import io.pelle.mango.client.base.vo.query.ComparisonOperator;
import io.pelle.mango.client.base.vo.query.IAliasProvider;
import io.pelle.mango.client.base.vo.query.IExpression;

import java.io.Serializable;

import com.google.common.base.Objects;

@SuppressWarnings("serial")
public class CompareExpression extends BaseBooleanExpression implements Serializable {

	private IExpression expression1;

	private ComparisonOperator comparisonOperator;

	private IExpression expression2;

	public CompareExpression() {
	}

	public CompareExpression(IExpression expression1, ComparisonOperator comparisonOperator, IExpression expression2) {
		super();
		this.expression1 = expression1;
		this.comparisonOperator = comparisonOperator;
		this.expression2 = expression2;
	}

	@Override
	public String getJPQL(IAliasProvider aliasProvider) {
		return (comparisonOperator.operand1Function(expression1.getJPQL(aliasProvider)) + " " + comparisonOperator.toString() + " " + comparisonOperator.operand2Function(expression2.getJPQL(aliasProvider)) + " " + super
				.getJPQL(aliasProvider)).trim();
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(this).add("expression1", expression1).add("operator", comparisonOperator).add("expression2", expression2).toString();
	}

}
