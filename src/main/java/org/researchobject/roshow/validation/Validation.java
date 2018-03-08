package org.researchobject.roshow.validation;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileUtils;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.graph.Factory;
import org.topbraid.shacl.validation.ValidationUtil;
import org.topbraid.shacl.util.ModelPrinter;


public class Validation {

    /**
     * an example of how to validate an rdf/turtle manifest aggregate-attribute against a shape that
     * imposes the necessary constraints for a research object aggregate schema i.e structural constraints
     */
    public static void main (String[] args) throws Exception {

        // Creates a memory Model and Graph and load the associated data and shape graphs
        Model shapeModel = ModelFactory.createModelForGraph(Factory.createDefaultGraph());
        Model dataModel = ModelFactory.createModelForGraph(Factory.createDefaultGraph());
        shapeModel.read(Validation.class.getResourceAsStream("/shape_graph/aggregate_shape.ttl"), null, FileUtils.langTurtle);
        dataModel.read(Validation.class.getResourceAsStream("/data_graph/aggregate_data.ttl"), null, FileUtils.langTurtle);

        // Perform the validation against the data model using the shape model
        Resource report = ValidationUtil.validateModel(dataModel, shapeModel, true);

        // Print violations
        System.out.println(ModelPrinter.get().print(report.getModel()));
    }

    /**
     * validate data graph against shape graph
     */
    public void validate(String data_graph, String data_graph_syntax, String shape_graph, String shape_graph_syntax) {

    }

    /**
     * @return the validation report from the ModelPrinter
     */
    public String getValidationReport() {
        return "report";
    }

}
