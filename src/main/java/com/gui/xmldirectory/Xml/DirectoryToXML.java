package com.gui.xmldirectory.Xml ;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class DirectoryToXML {

    public static void main(String[] args) {
        try {
            // Tạo một đối tượng DocumentBuilderFactory để xây dựng DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Tạo một Document mới
            Document doc = dBuilder.newDocument();

            // Tạo root element là "directory"
            Element rootElement = doc.createElement("directory");
            doc.appendChild(rootElement);

            // Thư mục bạn muốn chuyển thành XML (điều chỉnh đường dẫn của bạn tại đây)
            String directoryPath = "C:\\Users\\Admin\\Desktop\\Temp";

            // Gọi phương thức để thêm các thư mục và tập tin vào XML
            addDirectory(directoryPath, rootElement, doc);

            // Ghi XML ra một tập tin
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File("C:\\Users\\Admin\\Desktop\\directory.xml"));
            transformer.transform(source, result);

            // System.out.println("XML file created successfully!");

            Document doc_1 = readXMLFile("directory.xml");

            if (doc != null) {
                printDirectory(doc.getDocumentElement(), 0);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Document readXMLFile(String filePath) {
        try {
            // Tạo một đối tượng DocumentBuilderFactory để xây dựng DocumentBuilder
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

            // Đọc file XML
            Document doc = dBuilder.parse(new File(filePath));
            doc.getDocumentElement().normalize(); // Chuẩn hóa cấu trúc của Document (nếu cần)

            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void printDirectory(Element element, int level) {
        NodeList nodeList = element.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element childElement = (Element) node;
                String name = childElement.getAttribute("name");
                String type = childElement.getAttribute("type");

                StringBuilder indent = new StringBuilder();
                for (int j = 0; j < level; j++) {
                    indent.append("    "); // Thêm khoảng trắng để tạo hiệu ứng thụt vào
                }

                System.out.println(indent.toString() + name + " (" + type + ")");
                printDirectory(childElement, level + 1);
            }
        }
    }

    // Phương thức để thêm thư mục và tập tin vào XML
    private static void addDirectory(String dirPath, Element parentElement, Document doc) {
        File dir = new File(dirPath);
        File[] files = dir.listFiles();

        if (files != null) {
            for (File file : files) {
                Element fileElement = doc.createElement("file");

                // Tên tập tin hoặc thư mục
                fileElement.setAttribute("name", file.getName());

                if (file.isDirectory()) {
                    // Nếu là thư mục, đệ quy để thêm các tập tin bên trong
                    fileElement.setAttribute("type", "directory");
                    parentElement.appendChild(fileElement);
                    addDirectory(file.getAbsolutePath(), fileElement, doc);
                } else {
                    // Nếu là tập tin
                    fileElement.setAttribute("type", "file");
                    parentElement.appendChild(fileElement);
                }
            }
        }
    }
}
