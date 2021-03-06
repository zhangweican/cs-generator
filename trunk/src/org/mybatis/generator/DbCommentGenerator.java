package org.mybatis.generator;

import java.util.List;
import java.util.Properties;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.internal.util.StringUtility;

public class DbCommentGenerator
  implements CommentGenerator
{
  private Properties properties;
  private boolean suppressDate;
  private boolean suppressAllComments;

  public DbCommentGenerator()
  {
    this.properties = new Properties();
    this.suppressDate = true;
    this.suppressAllComments = false;
  }

  public void addJavaFileComment(CompilationUnit compilationUnit)
  {
  }

  public void addComment(XmlElement xmlElement)
  {
    if (this.suppressAllComments) {
      return;
    }

    xmlElement.addElement(new TextElement("<!--"));

    StringBuilder sb = new StringBuilder();
    sb.append("  WARNING - ");
    sb.append("@mbggenerated");
    xmlElement.addElement(new TextElement(sb.toString()));
    xmlElement.addElement(new TextElement("  This element is automatically generated by MyBatis Generator, do not modify."));

    String s = getDateString();
    if (s != null) {
      sb.setLength(0);
      sb.append("  This element was generated on ");
      sb.append(s);
      sb.append('.');
      xmlElement.addElement(new TextElement(sb.toString()));
    }

    xmlElement.addElement(new TextElement("-->"));
  }

  public void addRootComment(XmlElement rootElement)
  {
  }

  public void addConfigurationProperties(Properties properties)
  {
    this.properties.putAll(properties);

    this.suppressDate = StringUtility.isTrue(properties.getProperty("suppressDate"));

    this.suppressAllComments = StringUtility.isTrue(properties.getProperty("suppressAllComments"));
  }

  protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete)
  {
    javaElement.addJavaDocLine(" *");
    StringBuilder sb = new StringBuilder();
    sb.append(" * ");
    sb.append("@mbggenerated");
    if (markAsDoNotDelete) {
      sb.append(" do_not_delete_during_merge");
    }
    String s = getDateString();
    if (s != null) {
      sb.append(' ');
      sb.append(s);
    }
    javaElement.addJavaDocLine(sb.toString());
  }

  protected String getDateString()
  {
    return null;
  }

  public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    if (this.suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    innerClass.addJavaDocLine("/**");
    innerClass.addJavaDocLine(" * This class was generated by MyBatis Generator.");

    sb.append(" * This class corresponds to the database table ");
    sb.append(introspectedTable.getFullyQualifiedTable());
    innerClass.addJavaDocLine(sb.toString());

    addJavadocTag(innerClass, false);

    innerClass.addJavaDocLine(" */");
  }

  public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
    if (this.suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    innerEnum.addJavaDocLine("/**");
    innerEnum.addJavaDocLine(" * This enum was generated by MyBatis Generator.");

    sb.append(" * This enum corresponds to the database table ");
    sb.append(introspectedTable.getFullyQualifiedTable());
    innerEnum.addJavaDocLine(sb.toString());

    addJavadocTag(innerEnum, false);

    innerEnum.addJavaDocLine(" */");
  }

  public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    if (this.suppressAllComments) {
      return;
    }

    String name_table = introspectedTable.getFullyQualifiedTable().toString();
    String name_field = introspectedColumn.getActualColumnName();

    field.addJavaDocLine("/**<br/>");
    field.addJavaDocLine(" * 字段: " + name_table + "." + name_field + "<br/>");
    if (introspectedColumn.isIdentity()) {
      field.addJavaDocLine(" * 主键: 自动增长<br/>");
    }
    field.addJavaDocLine(" * 可空: " + introspectedColumn.isNullable() + "<br/>");
    field.addJavaDocLine(" * 缺省: " + (introspectedColumn.getDefaultValue() == null ? "" : introspectedColumn.getDefaultValue()) + "<br/>");
    if (introspectedColumn.getLength() > 0) {
      field.addJavaDocLine(" * 长度: " + introspectedColumn.getLength() + "<br/>");
    }
    field.addJavaDocLine(" * 说明: " + (introspectedColumn.getRemarks() == null ? "" : introspectedColumn.getRemarks()));

    field.addJavaDocLine(" */");
  }

  public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    if (this.suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    field.addJavaDocLine("/**");
    field.addJavaDocLine(" * This field was generated by MyBatis Generator.");

    sb.append(" * This field corresponds to the database table ");
    sb.append(introspectedTable.getFullyQualifiedTable());
    field.addJavaDocLine(sb.toString());

    addJavadocTag(field, false);

    field.addJavaDocLine(" */");
  }

  public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    if (this.suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    method.addJavaDocLine("/**");
    method.addJavaDocLine(" * This method was generated by MyBatis Generator.");

    sb.append(" * This method corresponds to the database table ");
    sb.append(introspectedTable.getFullyQualifiedTable());
    method.addJavaDocLine(sb.toString());

    addJavadocTag(method, false);

    method.addJavaDocLine(" */");
  }

  public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    if (this.suppressAllComments) {
      return;
    }

    String name_table = introspectedTable.getFullyQualifiedTable().toString();
    String name_field = introspectedColumn.getActualColumnName();

    method.addJavaDocLine("/**");

    method.addJavaDocLine(" * @return " + name_table + "." + name_field + ": " + (introspectedColumn.getRemarks() == null ? "" : introspectedColumn.getRemarks()));

    method.addJavaDocLine(" */");
  }

  public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
    if (this.suppressAllComments) {
      return;
    }

    String name_table = introspectedTable.getFullyQualifiedTable().toString();
    String name_field = introspectedColumn.getActualColumnName();

    method.addJavaDocLine("/**<br/>");

    Parameter parm = (Parameter)method.getParameters().get(0);

    method.addJavaDocLine(" * 字段: " + name_table + "." + name_field + "<br/>");
    if (introspectedColumn.isIdentity()) {
      method.addJavaDocLine(" * 主键: 自动增长<br/>");
    }
    method.addJavaDocLine(" * 可空: " + introspectedColumn.isNullable() + "<br/>");
    method.addJavaDocLine(" * 缺省: " + (introspectedColumn.getDefaultValue() == null ? "" : introspectedColumn.getDefaultValue()) + "<br/>");
    if (introspectedColumn.getLength() > 0) {
      method.addJavaDocLine(" * 长度: " + introspectedColumn.getLength() + "<br/>");
    }
    method.addJavaDocLine(" * @param " + parm.getName() + ": " + (introspectedColumn.getRemarks() == null ? "" : introspectedColumn.getRemarks()));

    method.addJavaDocLine(" */");
  }

  public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
    if (this.suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    innerClass.addJavaDocLine("/**");
    innerClass.addJavaDocLine(" * This class was generated by MyBatis Generator.");

    sb.append(" * This class corresponds to the database table ");
    sb.append(introspectedTable.getFullyQualifiedTable());
    innerClass.addJavaDocLine(sb.toString());

    addJavadocTag(innerClass, markAsDoNotDelete);

    innerClass.addJavaDocLine(" */");
  }
}