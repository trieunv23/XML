package com.gui.xmldirectory.Xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class ObjectToXML {
    public static void main(String[] args) throws ParserConfigurationException {
        Student st1 = new Student("Trieu", 18, 3.0);
        Student st2 = new Student("Khang", 18, 3.4);
        List<Student> list = new ArrayList<>();
        list.add(st1);
        list.add(st2);

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // Tạo Document
        Document document = builder.newDocument();

        // Tạo root element
        Element rootElement = document.createElement("Persons");
        document.appendChild(rootElement);

        // Duyệt qua danh sách và tạo các element cho mỗi đối tượng
        for (Student st : list) {
            Element personElement = document.createElement("Student");
            rootElement.appendChild(personElement);

            Element name = document.createElement("Name");
            name.appendChild(document.createTextNode(st.getName()));
            personElement.appendChild(name);

            Element age = document.createElement("Age");
            age.appendChild(document.createTextNode(String.valueOf(st.getAge())));
            personElement.appendChild(age);

            Element gender = document.createElement("GPA");
            gender.appendChild(document.createTextNode(String.valueOf(st.getGpa())));
            personElement.appendChild(gender);
        }

        // Chuyển đổi Document thành XML
        StringWriter writer;
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
        } catch (TransformerException ex) {
            throw new RuntimeException(ex);
        }

        String xmlString = writer.getBuffer().toString();
        System.out.println(xmlString);
    }
}
