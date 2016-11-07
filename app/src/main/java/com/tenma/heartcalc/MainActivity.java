package com.tenma.heartcalc;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hanks.htextview.HTextView;
import com.hanks.htextview.HTextViewType;
import com.hanks.htextview.animatetext.HText;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
*
* 主要游戏画面
*
* */
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    /** 第一个数的控件 */
    private HTextView mFirstNum;
    /** 连接符控件 */
    private HTextView mLinkChar;
    /** 第二个数的控件 */
    private HTextView mSecondNum;
    /** 分数控件 */
    private HTextView mScore;
    /** 第一个的答案的控件 */
    private TextView mAnswerA;
    /** 第二个的答案的控件 */
    private TextView mAnswerB;
    /** 第三个的答案的控件 */
    private TextView mAnswerC;
    /** 随机数生产工具 */
    private Random mRandom;
    /** handler每次收到消息都进行一次UI更新 */
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            int firstNum = mRandom.nextInt(100);
            int secondNum = mRandom.nextInt(100);
            setmFirstNum(firstNum+"");
            setmSecondNum(secondNum+"");
            switch (firstNum%4){
                case 0:
                    setmLinkChar("+");
                    break;
                case 1:
                    setmLinkChar("-");
                    break;
                case 2:
                    setmLinkChar("*");
                    break;
                case 3:
                    setmLinkChar("/");
                    break;
                default:
                    setmLinkChar("+");
            }
            int bigRect = mRandom.nextInt(100)+1;
            int smallRect = mRandom.nextInt(100)+1;
            String[] results = {getResult()+"",(getResult()+bigRect)+"",(getResult()-smallRect)+""};
            setAnswers(results);
            return false;
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }


    /**
     *  初始化方法
     *
     * */
    private void init(){
        mFirstNum = (HTextView) findViewById(R.id.firstNum);
        mLinkChar = (HTextView) findViewById(R.id.linkChar);
        mSecondNum = (HTextView) findViewById(R.id.secondNum);
        mScore = (HTextView) findViewById(R.id.score);
        mScore.animateText("0");
        mAnswerA = (TextView) findViewById(R.id.answerA);
        mAnswerB = (TextView) findViewById(R.id.answerB);
        mAnswerC = (TextView) findViewById(R.id.answerC);
        mAnswerA.setOnClickListener(this);
        mAnswerB.setOnClickListener(this);
        mAnswerC.setOnClickListener(this);
        mRandom = new Random();
        mHandler.sendEmptyMessage(0);
    }

    /**
     *  设置第一位数
     *
     *  @param num 第一位数
     *
     * */
    private void setmFirstNum(String num){
        mFirstNum.setAnimateType(HTextViewType.EVAPORATE);
        mFirstNum.animateText(num);
    }

    /**
     *  设置第二位数
     *
     *  @param num 第二位数
     *
     * */
    private void setmSecondNum(String num){
        mSecondNum.setAnimateType(HTextViewType.EVAPORATE);
        mSecondNum.animateText(num);
    }

    /**
     *  设置第运算符
     *
     *  @param linkChar 运算符
     *
     * */
    private void setmLinkChar(String linkChar) {
        mLinkChar.setAnimateType(HTextViewType.ANVIL);
        mLinkChar.animateText(linkChar);
    }

    /**
     *  设置答案的值分三种情况，数组中第一位是正确答案，把正确答案随机赋值到A、B、C位。
     *
     *  @param answers 答案
     *
     * */
    private void setAnswers(String[] answers){
        int answerMode = mRandom.nextInt(2);
        switch (answerMode){
            case 0:
                mAnswerA.setText(answers[0]);
                mAnswerB.setText(answers[1]);
                mAnswerC.setText(answers[2]);
                break;
            case 1:
                mAnswerA.setText(answers[1]);
                mAnswerB.setText(answers[0]);
                mAnswerC.setText(answers[2]);
                break;
            case 2:
                mAnswerA.setText(answers[1]);
                mAnswerB.setText(answers[2]);
                mAnswerC.setText(answers[0]);
                break;
            default:
                mAnswerA.setText(answers[0]);
                mAnswerB.setText(answers[1]);
                mAnswerC.setText(answers[2]);
        }
    }

    /**
     *  获取正确的答案值
     *
     *  @return 正确答案
     *
     * */
    private int getResult(){
        float firstNum = Float.parseFloat(mFirstNum.getText().toString());
        float secondNum = Float.parseFloat(mSecondNum.getText().toString());
        String linkChar = mLinkChar.getText().toString();
        int result = 0;
        switch (linkChar) {
            case "+":
                result = (int)(firstNum + secondNum);
                break;
            case "-":
                result = (int)(firstNum - secondNum);
                break;
            case "*":
                result = (int)(firstNum * secondNum);
                break;
            case "/":
                result = (int)(firstNum / secondNum);
                break;
        }
        return result;
    }

    @Override
    public void onClick(View v) {
        TextView selectView = (TextView) findViewById(v.getId());
        mScore.setAnimateType(HTextViewType.FALL);
        if (selectView.getText().toString().equals(getResult()+"")){
            Snackbar.make(selectView,"正确",Snackbar.LENGTH_SHORT).setAction("action",null).show();
            mScore.animateText((Integer.parseInt(mScore.getText().toString())+10)+"");
        }else{
            Snackbar.make(selectView,"错误",Snackbar.LENGTH_SHORT).setAction("action",null).show();
            mScore.animateText((Integer.parseInt(mScore.getText().toString())-10)+"");
        }
        mHandler.sendEmptyMessage(0);
    }
}
