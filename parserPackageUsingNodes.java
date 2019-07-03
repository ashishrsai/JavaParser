package parsing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseStart;
import com.github.javaparser.Range;
import com.github.javaparser.StreamProvider;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.resolution.SymbolResolver;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import com.github.javaparser.resolution.declarations.ResolvedClassDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedMethodDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.javaparsermodel.JavaParserFacade;
import com.github.javaparser.symbolsolver.javaparsermodel.declarations.JavaParserMethodDeclaration;
import com.github.javaparser.symbolsolver.model.resolution.SymbolReference;
import com.github.javaparser.symbolsolver.model.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.reflectionmodel.ReflectionMethodDeclaration;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;




public class parserPackageUsingNodes implements SymbolResolver{

	 HashMap<String, NodeCode> entities = new HashMap<String,NodeCode> ();
	 String currentEnt;
	 private  CompilationUnit compilationUnit;
	 int uu = 0;
	 ArrayList<MethodCallRawPairs> methodCalls = new ArrayList<MethodCallRawPairs> ();
	 String codes = "";
	 TypeSolver typeSolver = new ReflectionTypeSolver();
	 
public  void parsThis(String addr) throws FileNotFoundException {
		
		
		//File folder = new File("C:/Users/farshad.toosi/Documents/Work-Spaces/workspace-jhotdraw-source/workspace-jhotdraw/JHotDraw7");
		File folder = new File(addr);
		//File folder = new File("/Users/farshadtoosi/Documents/Software/workspace-jhotdraw");
		
		listFilesForFolder(folder);
		
	}
	
	
	
	public  void listFilesForFolder(final File folder) throws FileNotFoundException {
		//System.out.println(folder.getName());
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	            //System.out.println(fileEntry.getName());
	        	if(fileEntry.getName().endsWith(".java")) {
	        		if(fileEntry.getName().contains("fery"))
	        		ActualParse(fileEntry);
	        		//System.out.println(u++);
	        	}
	        }
	    }
	}
	
	
	public void inject(CompilationUnit destination) { 
        destination.setData(Node.SYMBOL_RESOLVER_KEY, this); 
    } 
	
	public  void ActualParse(File file) throws FileNotFoundException {
		JavaParser.getStaticConfiguration().setAttributeComments(false);
		compilationUnit = JavaParser.parse(file);
		inject(compilationUnit);
		new PackagesFile().visit(compilationUnit, file);
		
		
	}
	
	
	
	
	
	private  class PackagesFile extends VoidVisitorAdapter
    {
        
        public void visit(PackageDeclaration packag, Object file)  {
          //System.out.println("Package Declration: " + packag.getName() + "\t");
          File fileC = (File) file;
          entities.put(packag.getName().toString(), new NodeCode(packag,""));
          
          new FilesClazz1().visit(compilationUnit, packag);
          
        }
        
        
        
    
	
	private  class FilesClazz1 extends VoidVisitorAdapter  {
        public void visit(ClassOrInterfaceDeclaration claz, Object CurrEntity) {
        	
        	claz.getChildNodes().forEach(e -> {
        		System.out.println("\t"+claz.getClass().toString()+"\t"+e.getClass().toString());
        		recursive1Childern1(e);
        			
        	});
        	
        }
	}
	
	private void recursive1Childern1(Node node) {
		uu++;
		node.getChildNodes().forEach(child -> {
				System.out.println(node.getClass().toString()+"\t"+child.getClass().toString());
				recursive1Childern1(child);
		});
	}
	
	
	
	
	private  class FilesClazz extends VoidVisitorAdapter  {
        public void visit(ClassOrInterfaceDeclaration claz, Object CurrEntity) {
        	//System.out.println(((PackageDeclaration) CurrEntity).getNameAsString()+":"+claz.getNameAsString()+"--------");
        	
        	
        	claz.setParentNode(((PackageDeclaration) CurrEntity));
        	
        	codes = "";
        	recursiveChildern(claz);
        	entities.put(((PackageDeclaration) CurrEntity).getNameAsString()+":"+claz.getNameAsString(), new NodeCode(claz,codes));
        	
        	
        	claz.getMethods().forEach(method -> {
        		currentEnt = ((PackageDeclaration) CurrEntity).getNameAsString()+":"+claz.getNameAsString()+":"+method.getNameAsString();
        		
        		
        		//System.out.println(method.getName());
        		codes = "";
        		recursiveChildern(method);
        		entities.put(currentEnt, new NodeCode(method,codes));
        	});
        	
        	claz.getConstructors().forEach(cont -> {
        		currentEnt = ((PackageDeclaration) CurrEntity).getNameAsString()+":"+claz.getNameAsString()+":"+cont.getNameAsString();
        		codes = "";
        		recursiveChildern(cont);
        		entities.put(currentEnt, new NodeCode(cont,codes));
        	});
        	

        	claz.getFields().forEach(fild -> {
        		currentEnt = ((PackageDeclaration) CurrEntity).getNameAsString()+":"+claz.getNameAsString();        	
        		fild.getVariables().forEach(v ->{        			
        			entities.put(currentEnt+":"+v.removeInitializer().toString(), new NodeCode(v,""));
        		});
        	});
        }
    }
	
	private void recursiveChildern(Node node) {
		node.getChildNodes().forEach(child -> {
			if(child instanceof VariableDeclarationExpr) {
				
				//codes += "$$"+child.toString();
				((VariableDeclarationExpr) child).getVariables().forEach(v ->{
					//System.out.println("\t"+v.toString()+"\t"+v.removeInitializer().toString());
					
					entities.put(currentEnt+":"+v.removeInitializer().toString(), new NodeCode(v,""));
					
				});
			}else
			if(child instanceof AssignExpr) {
				//codes += "$$"+child.toString();
			}else
			if(child instanceof BinaryExpr) {
				codes += "$$"+child.toString();
			}
			if(child instanceof UnaryExpr) {
				//codes += "$$"+child.toString();
			}
			if(child instanceof MethodCallExpr) {
				((MethodCallExpr) child).getChildNodesByType(MethodCallExpr.class).forEach(c -> {
					//codes += "$$"+c.toString();
				});
				
				if(child.getChildNodesByType(MethodCallExpr.class).size()==0) {
					//codes += "$$"+child.toString();
				}
			}
			else
				recursiveChildern(child);
		});
	}
	
	
	
	
	String findExprAssignment(String m1, String m2) {
		if(m1.contains(m2))
			return m1.replace(m2, "");
		return "";
	}



		
}





	@Override
	public ResolvedType calculateType(Expression expression) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public <T> T resolveDeclaration(Node node, Class<T> resultClass) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public <T> T toResolvedType(Type javaparserType, Class<T> resultClass) {
		// TODO Auto-generated method stub
		return null;
	}
}
