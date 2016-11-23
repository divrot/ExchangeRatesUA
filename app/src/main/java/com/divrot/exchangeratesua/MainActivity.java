package com.divrot.exchangeratesua;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends Activity {

    TextView textViewRates;
    Button btnRates;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // найдем View-элементы
        textViewRates = (TextView) findViewById(R.id.textViewRates);
        btnRates = (Button) findViewById(R.id.buttonRates);

        // создаем обработчик нажатия
        OnClickListener oCbtnRates = new OnClickListener() {
            @Override
            public void onClick(View v) {

                textViewRates = (TextView) findViewById(R.id.textViewRates);
                // Меняем текст в TextView (tvOut)
                try{
 /*
  определяем URL сервиса
  готовим API, позволяющий выполнять разбор документа
  загружаем в парсер полученный ответ и вызываем метод parse
  */
                    URL url = new URL("https://privat24.privatbank.ua/p24/accountorder?oper=prp&PUREXML&apicour&country=");
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document doc = db.parse(new InputSource(url.openStream()));
                    doc.getDocumentElement().normalize();
 /*получаем агрегатный узел с дочерними узлами с атрибутами, хранящими значения валют;
 в ответе всего два узла, мы возьмем первый, а при необходимости тут вполне можно запустить цикл с nodeList.getLength
*/

                    NodeList nodeList = doc.getElementsByTagName("exchangerate");
                    Node node = nodeList.item(0);
                    // опускаемся на узел ниже и получаем список его атрибутов
                    NamedNodeMap attributes = node.getFirstChild().getAttributes();
                    //получаем значение атрибут buy
                    Node currencyAttribEUR  = attributes.getNamedItem("buy");
                    // ... и его значение
                    String currencyValueEUR = currencyAttribEUR.getNodeValue();

                    // аналогично поступаем с датой, чтобы иметь представление о актуальности
                    Node dateCurrency       = attributes.getNamedItem("date");
                    String dateCurrencyStr  = dateCurrency.getNodeValue();
                    // и выводим информацию
                    textViewRates.setText("Курс евро на "+dateCurrencyStr+":"+currencyValueEUR+ "коп");

                }
                catch (Exception e) {
                    textViewRates.setText("Не удалось выполнить операцию");
                }
            }
        };
        btnRates.setOnClickListener(oCbtnRates);
    }

}

