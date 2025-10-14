package com.ehappy.exmultibutton;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private TextView tvExpr, tvResult;

    private final List<String> tokens = new ArrayList<String>();
    private final StringBuilder currentNumber = new StringBuilder();
    private final MathContext MC = new MathContext(16);
    private boolean justEvaluated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvExpr = findViewById(R.id.displayOperation);
        tvResult = findViewById(R.id.result);

        // 數字鍵
        int[] digitIds = {
                R.id.btnZero, R.id.btnOne, R.id.btnTwo, R.id.btnThree, R.id.btnFour,
                R.id.btnFive, R.id.btnSix, R.id.btnSeven, R.id.btnEight, R.id.btnNine
        };
        View.OnClickListener digitClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String d = ((Button) v).getText().toString();
                appendDigit(d);
            }
        };
        for (int id : digitIds) {
            findViewById(id).setOnClickListener(digitClick);
        }

        // 二元運算子
        int[] opIds = { R.id.plus, R.id.sub, R.id.mul, R.id.div };
        View.OnClickListener opClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String op = ((Button) v).getText().toString();

                // 先把正在輸入的數字收進 tokens
                pushCurrentNumberIfAny();

                String last = lastToken();

                // A. 若最後是運算子：覆蓋（更改運算子）
                if (last != null && isOperator(last)) {
                    tokens.set(tokens.size() - 1, op);
                    refreshDisplay();
                    return; // 這裡絕不觸發一元負號
                }

                // B. 允許一元負號的兩個情境：在開頭、或在 '(' 後
                if (op.equals("-")) {
                    if (last == null) {
                        // 開頭：開始輸入一個負數，而不是顯示 0-
                        currentNumber.setLength(0);
                        currentNumber.append("-");
                        refreshDisplay();
                        return;
                    } else if ("(".equals(last)) {
                        // 在括號後允許 - 作為一元負號
                        tokens.add("0");
                        tokens.add("-");
                        refreshDisplay();
                        return;
                    }
                }

                // C. 一般情況：只有在最後是「數字或右括號」才可接二元運算子
                if (last != null && (isNumber(last) || ")".equals(last))) {
                    tokens.add(op);
                    refreshDisplay();
                }
                // 其他情況（例如一開始就按 +、或在 '(' 後按 +/*/）直接忽略

                // 若剛剛按過等號
                if (justEvaluated) {
                    justEvaluated = false;
                    // 確保結果存在 tokens，並允許繼續運算
                    pushCurrentNumberIfAny();
                }

            }
        };
        for (int id : opIds) {
            findViewById(id).setOnClickListener(opClick);
        }

        // 括號
        findViewById(R.id.left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokens.add("(");
                refreshDisplay();
            }
        });
        findViewById(R.id.right).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushCurrentNumberIfAny();
                tokens.add(")");
                refreshDisplay();
            }
        });

        // ^2
        findViewById(R.id.sqr).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentNumber.length() > 0) {
                    BigDecimal x = new BigDecimal(currentNumber.toString());
                    BigDecimal y = x.multiply(x, MC);
                    setCurrentNumber(y.stripTrailingZeros().toPlainString());
                    tvResult.setText(currentNumber.toString());
                    return;
                }
                String last = lastToken();
                if (last != null && isNumber(last)) {
                    BigDecimal x = new BigDecimal(last);
                    BigDecimal y = x.multiply(x, MC);
                    tokens.set(tokens.size() - 1, y.stripTrailingZeros().toPlainString());
                    tvResult.setText(y.stripTrailingZeros().toPlainString());
                    refreshDisplay();
                }
            }
        });

        // SQRT
        findViewById(R.id.sqrt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentNumber.length() > 0) {
                    double x = new BigDecimal(currentNumber.toString()).doubleValue();
                    if (x < 0) {
                        tvResult.setText("NaN");
                        return;
                    }
                    double y = Math.sqrt(x);
                    setCurrentNumber(trimDouble(y));
                    tvResult.setText(currentNumber.toString());
                    return;
                }
                String last = lastToken();
                if (last != null && isNumber(last)) {
                    double x = new BigDecimal(last).doubleValue();
                    if (x < 0) {
                        tvResult.setText("NaN");
                        return;
                    }
                    double y = Math.sqrt(x);
                    tokens.set(tokens.size() - 1, trimDouble(y));
                    tvResult.setText(trimDouble(y));
                    refreshDisplay();
                }
            }
        });


        // +/-
        findViewById(R.id.not).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 若有正在輸入的數字：切換該數字正負
                if (currentNumber.length() > 0) {
                    if (currentNumber.charAt(0) == '-') {
                        currentNumber.deleteCharAt(0);
                    } else {
                        currentNumber.insert(0, '-');
                    }
                    tvResult.setText(currentNumber.toString());
                    refreshDisplay();
                    return;
                }

                // 若沒有正在輸入的數字：
                // 先看是否剛輸入完一個運算子（準備輸入下一個數字）
                String last = lastToken();
                if (last == null || isOperator(last) || "(".equals(last)) {
                    // 允許以 +/- 開始一個負數
                    if (currentNumber.length() == 0) {
                        currentNumber.append("-");
                    } else if (currentNumber.length() == 1 && currentNumber.charAt(0) == '-') {
                        currentNumber.setLength(0); // 再按一次恢復空
                    }
                    tvResult.setText(currentNumber.toString());
                    refreshDisplay();
                    return;
                }

                // 否則（最後是數字或 ')'），你也可以選擇「就地切換上一個數字的正負」
                if (last != null && isNumber(last)) {
                    if (last.startsWith("-")) {
                        tokens.set(tokens.size() - 1, last.substring(1));
                        tvResult.setText(last.substring(1));
                    } else {
                        tvResult.setText("-" + last);
                        tokens.set(tokens.size() - 1, "-" + last);
                    }
                    refreshDisplay();
                }
            }
        });

        // DEL
        findViewById(R.id.del).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentNumber.length() > 0) {
                    currentNumber.deleteCharAt(currentNumber.length() - 1);
                } else if (!tokens.isEmpty()) {
                    tokens.remove(tokens.size() - 1);
                }
                refreshDisplay();
            }
        });

        // CE
        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokens.clear();
                currentNumber.setLength(0);
                tvResult.setText("");
                refreshDisplay();
                justEvaluated = false;
            }
        });


        // =
        findViewById(R.id.sum).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushCurrentNumberIfAny();
                try {
                    BigDecimal ans = evaluate(tokens);
                    tvResult.setText(ans.stripTrailingZeros().toPlainString());
                    // 清空舊表達式，改成以結果作為新起點
                    tokens.clear();
                    tokens.add(ans.stripTrailingZeros().toPlainString());
                    currentNumber.setLength(0);
                    refreshDisplay();
                    justEvaluated = true; // ✅ 標記已完成計算
                } catch (Exception e) {
                    tvResult.setText("Error");
                }
            }
        });

    }

    // ====== 輔助 ======
    private void appendDigit(String d) {
        // 若剛按完等號且沒有運算子，清空重新開始
        if (justEvaluated) {
            tokens.clear();
            currentNumber.setLength(0);
            tvResult.setText("");
            justEvaluated = false;
        }

        currentNumber.append(d);
        tvResult.setText(currentNumber.toString());
        refreshDisplay();
    }


    private void pushCurrentNumberIfAny() {
        if (currentNumber.length() > 0) {
            tokens.add(currentNumber.toString());
            currentNumber.setLength(0);
        }
    }

    private void setCurrentNumber(String s) {
        currentNumber.setLength(0);
        currentNumber.append(s);
        tvResult.setText(currentNumber.toString());
        refreshDisplay();
    }

    private void refreshDisplay() {
        StringBuilder sb = new StringBuilder();
        for (String t : tokens) sb.append(t).append(" ");
        if (currentNumber.length() > 0) sb.append(currentNumber);
        tvExpr.setText(sb.toString().trim());
    }

    private String trimDouble(double v) {
        String s = BigDecimal.valueOf(v).stripTrailingZeros().toPlainString();
        return s.equals("-0") ? "0" : s;
    }

    // ====== 運算核心 ======
    private BigDecimal evaluate(List<String> infix) {
        List<String> rpn = toRPN(infix);
        Deque<BigDecimal> st = new ArrayDeque<BigDecimal>();

        for (String t : rpn) {
            if (isOperator(t)) {
                BigDecimal b = st.pop();
                BigDecimal a = st.isEmpty() ? BigDecimal.ZERO : st.pop(); // 支援單項負號
                BigDecimal r = BigDecimal.ZERO;

                if (t.equals("+")) r = a.add(b, MC);
                else if (t.equals("-")) r = a.subtract(b, MC);
                else if (t.equals("*")) r = a.multiply(b, MC);
                else if (t.equals("/")) {
                    if (b.compareTo(BigDecimal.ZERO) == 0)
                        throw new ArithmeticException("Division by zero");
                    r = a.divide(b, MC);
                }
                st.push(r);
            } else {
                st.push(new BigDecimal(t));
            }
        }
        return st.pop();
    }

    private boolean isOperator(String t) {
        return "+".equals(t) || "-".equals(t) || "*".equals(t) || "/".equals(t);
    }

    private String lastToken() {
        return tokens.isEmpty() ? null : tokens.get(tokens.size() - 1);
    }

    private List<String> toRPN(List<String> infix) {
        Map<String, Integer> prec = new HashMap<String, Integer>();
        prec.put("+", 1);
        prec.put("-", 1);
        prec.put("*", 2);
        prec.put("/", 2);

        List<String> out = new ArrayList<String>();
        Deque<String> ops = new ArrayDeque<String>();
        String prev = ""; // 前一個 token，用來判斷單項負號

        for (String t : infix) {
            if (isNumber(t)) {
                out.add(t);
            } else if (t.equals("(")) {
                ops.push(t);
            } else if (t.equals(")")) {
                while (!ops.isEmpty() && !ops.peek().equals("(")) {
                    out.add(ops.pop());
                }
                if (!ops.isEmpty() && ops.peek().equals("(")) ops.pop();
            } else if (isOperator(t)) {
                // 處理單項負號（如 (-3 + 2) 或開頭 -5）
                if (t.equals("-") && (prev.equals("") || isOperator(prev) || prev.equals("("))) {
                    out.add("0");
                }
                while (!ops.isEmpty() && !ops.peek().equals("(")
                        && prec.get(ops.peek()) >= prec.get(t)) {
                    out.add(ops.pop());
                }
                ops.push(t);
            }
            prev = t;
        }

        while (!ops.isEmpty()) out.add(ops.pop());
        return out;
    }

    private boolean isNumber(String s) {
        try {
            new BigDecimal(s);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}