package de.hbrs.aspgen.generator.container;

import java.util.LinkedList;
import java.util.List;

import de.hbrs.aspgen.api.ast.JavaMethod;
import de.hbrs.aspgen.api.ast.JavaParameter;
import de.hbrs.aspgen.generator.builder.AspectJBlock;
import de.hbrs.aspgen.generator.builder.ContentPart;
import de.hbrs.aspgen.generator.builder.StaticLine;

public abstract class AdviceContainer extends AspectJContainer implements AspectJBlock {
    protected final List<ContentPart> contentParts = new LinkedList<>();
    protected JavaMethod method;
    protected List<String> softeningExceptions = new LinkedList<>();
    private boolean addThisToAdviceDeclaration = false;
    private boolean isInterface = false;

    public void setMethod(final JavaMethod javaMethod) {
        method = javaMethod;
    }

    public void addLine(final String staticPart) {
        contentParts.add(new StaticLine(staticPart));
    }

    public void isInterface() {
        isInterface = true;
    }

    protected abstract String getAdviceType(final JavaMethod javaMethod);
    protected abstract String getAdditionalAdviceDeclartaation(final JavaMethod javaMethod);

    protected String createArgsPointcut(final JavaParameter parameter) {
        final String args = " && args(" + getArgsForOneParameter(parameter) + ")";
        if (addThisToAdviceDeclaration) {
            return args + " && this(" + lowerCharAtFirstPos(onType) + ")";
        } else {
            return args;
        }
    }

    private String getArgsForOneParameter(final JavaParameter parameter) {
        final StringBuffer buffer = new StringBuffer();
        boolean isFirst = true;
        for (final JavaParameter parameterFromMethod : method.getParameters()) {
            if (isFirst) {
                isFirst = false;
            } else {
                buffer.append(", ");
            }
            if (parameterFromMethod.getName().equals(parameter.getName())
                    && parameterFromMethod.getType().equals(parameter.getType())) {
                buffer.append(parameter.getName());
            } else {
                buffer.append("*");
            }
        }
        return buffer.toString();
    }

    protected String getParameterList(final JavaParameter parameter) {
        if (addThisToAdviceDeclaration) {
            return  "(final " + addThisAdviceDeclaration() + ", " + getParameterFromParameter(parameter) + ") ";
        } else {
            return  "(" + getParameterFromParameter(parameter) + ") ";
        }
    }

    protected String getParameterList(final JavaMethod method) {
        if (addThisToAdviceDeclaration && method.getParameters().size() == 0) {
            return "(final " + addThisAdviceDeclaration() + ") ";
        } else if (addThisToAdviceDeclaration) {
            return "(final " + addThisAdviceDeclaration() + ", " + getParameterFromMethod(method) + ") ";
        } else {
            return "(" + getParameterFromMethod(method) + ") ";
        }
    }

    private String addThisAdviceDeclaration() {
        return onType + " " + lowerCharAtFirstPos(onType);
    }

    // TODO in string utils oder so packen method for field
    private String lowerCharAtFirstPos(final String string) {
        final String charAtBegin = string.substring(0, 1);
        return charAtBegin.toLowerCase() + string.substring(1);
    }

    protected String getParameterFromParameter(final JavaParameter parameter) {
        return "final " + parameter.getType() + " " + parameter.getName();
    }

    protected String createAdviceBody(final JavaParameter parameter, final JavaMethod method) {
        String methodBodyContent = "";
        for (final ContentPart part : contentParts) {
            methodBodyContent += replacer.replace(part.getContent(), onType, method, parameter);
        }
        return methodBodyContent.replace("$proceedcall$", createProceedCall(parameter))
                .replace("$returnproceedcall$", createReturnProceedCall(parameter))
                .replace("$storeproceedcall$", createStoreProceedCall(parameter))
                .replace("$returnstoredproceedcall$", returnStoreProceedCall(parameter));
    }

    protected String createStoreProceedCall(final JavaParameter parameter) {
        String returnPrefix;
        if (method.getType().equals("void")) {
            returnPrefix = "";
        } else {
            returnPrefix = method.getType() + " result = ";
        }

        if (addThisToAdviceDeclaration) {
            return returnPrefix + "proceed(" + lowerCharAtFirstPos(onType) + ", " + parameter.getName() +");";
        } else {
            return returnPrefix + "proceed(" + parameter.getName() +");";
        }
    }

    protected String returnStoreProceedCall(final JavaParameter parameter) {
        if (method.getType().equals("void")) {
            return "";
        } else {
            return "return result;";
        }
    }


    private String createReturnProceedCall(final JavaParameter parameter) {
        String returnPrefix;
        if (method.getType().equals("void")) {
            returnPrefix = "";
        } else {
            returnPrefix = "return ";
        }
        if (addThisToAdviceDeclaration) {
            return returnPrefix + "proceed(" + lowerCharAtFirstPos(onType) + ", " + parameter.getName() +");";
        } else {
            return returnPrefix + "proceed(" + parameter.getName() +");";
        }
    }

    private String createProceedCall(final JavaParameter parameter) {
        if (addThisToAdviceDeclaration) {
            return "proceed(" + lowerCharAtFirstPos(onType) + ", " + parameter.getName() +")";
        } else {
            return "proceed(" + parameter.getName() +")";
        }
    }

    protected String getParameterFromMethod(final JavaMethod javaMethod) {
        String result = "";
        boolean isFirst = true;
        for (final JavaParameter javaParameter : javaMethod.getParameters()) {
            if (isFirst) {
                isFirst = false;
            } else {
                result += ", ";
            }
            result += "final " + javaParameter.getType() + " " + javaParameter.getName();
        }
        return result;
    }

    protected String createPointcut(final JavaMethod javaMethod) {
        final String returnType = javaMethod.getType();
        final String methodName = javaMethod.getName();
        final String interfaceMarker = isInterface ? "+" : "";
        final String accessType = isInterface && javaMethod.getAccessType().isEmpty() ? "public" : javaMethod.getAccessType();
        return "execution("
                + accessType + SPACE
                + returnType + SPACE
                + onType + interfaceMarker + "." + methodName + "(" + getParameterTypeFromMethod(javaMethod) + ")"
                + ")";
    }

    protected String createArgsPointcut(final JavaMethod javaMethod) {
        String argsPointcut = "";

        if (javaMethod.getParameters().size() != 0) {
            argsPointcut += " && args(" + getParameterNameFromMethod(javaMethod) + ")";
        }

        if (addThisToAdviceDeclaration) {
            argsPointcut += " && this(" + lowerCharAtFirstPos(onType) + ")";
        }

        return argsPointcut;
    }

    private String getParameterTypeFromMethod(final JavaMethod javaMethod) {
        String result = "";
        boolean isFirst = true;
        for (final JavaParameter javaParameter : javaMethod.getParameters()) {
            if (isFirst) {
                isFirst = false;
            } else {
                result += ", ";
            }
            result += javaParameter.getType();
        }
        return result;
    }

    private String getParameterNameFromMethod(final JavaMethod javaMethod) {
        String result = "";
        boolean isFirst = true;
        for (final JavaParameter javaParameter : javaMethod.getParameters()) {
            if (isFirst) {
                isFirst = false;
            } else {
                result += ", ";
            }
            result += javaParameter.getName();
        }
        return result;
    }

    protected String createProceedCall(final JavaMethod javaMethod) {
        if (addThisToAdviceDeclaration && method.getParameters().size() > 0) {
            return "proceed(" + lowerCharAtFirstPos(onType) + ", " + getParameterNameFromMethod(javaMethod) + ")";
        } else if (addThisToAdviceDeclaration) {
            return "proceed(" + lowerCharAtFirstPos(onType) + ")";
        } else if (method.getParameters().size() > 0) {
            return "proceed(" + getParameterNameFromMethod(javaMethod) + ")";
        } else {
            return "proceed()";
        }
    }

    protected String createReturnProceedCall(final JavaMethod javaMethod) {
        String returnPrefix;
        if (method.getType().equals("void")) {
            returnPrefix = "";
        } else {
            returnPrefix = "return ";
        }

        if (addThisToAdviceDeclaration && method.getParameters().size() > 0) {
            return returnPrefix + "proceed(" + lowerCharAtFirstPos(onType) + ", " + getParameterNameFromMethod(javaMethod) + ");";
        } else if (addThisToAdviceDeclaration) {
            return returnPrefix + "proceed(" + lowerCharAtFirstPos(onType) + ");";
        } else if (method.getParameters().size() > 0) {
            return returnPrefix + "proceed(" + getParameterNameFromMethod(javaMethod) + ");";
        } else {
            return returnPrefix + "proceed();";
        }
    }

    protected String createStoreProceedCall(final JavaMethod javaMethod) {
        String returnPrefix;
        if (method.getType().equals("void")) {
            returnPrefix = "";
        } else {
            returnPrefix = method.getType() + " result = ";
        }

        if (addThisToAdviceDeclaration && method.getParameters().size() > 0) {
            return returnPrefix + "proceed(" + lowerCharAtFirstPos(onType) + ", " + getParameterNameFromMethod(javaMethod) + ");";
        } else if (addThisToAdviceDeclaration) {
            return returnPrefix + "proceed(" + lowerCharAtFirstPos(onType) + ");";
        } else if (method.getParameters().size() > 0) {
            return returnPrefix + "proceed(" + getParameterNameFromMethod(javaMethod) + ");";
        } else {
            return returnPrefix + "proceed();";
        }
    }

    protected String returnStoreProceedCall(final JavaMethod javaMethod) {
        if (method.getType().equals("void")) {
            return "";
        } else {
            return "return result;";
        }
    }

    public void addSofteningExcption(final String string) {
        softeningExceptions.add(string);
    }


    protected String createSofteningsExceptions(final JavaMethod method, final String annotationPartForSoftening) {
        if (softeningExceptions.size() == 0) {
            return "";
        }
        final StringBuffer sb = new StringBuffer();
        int counter = 1;
        for (final String exception : softeningExceptions) {
            sb.append(TAB + "//" + annotationPartForSoftening.replace("$counterplaceholder$", String.valueOf(counter++)) + NEWLINE);
            sb.append(TAB + "declare soft : " + exception + " : " + createPointcut(method) + ";" + NEWLINE);
        }
        sb.append(NEWLINE);
        return sb.toString();
    }


    public void addThisParameter() {
        addThisToAdviceDeclaration = true;
    }
}
