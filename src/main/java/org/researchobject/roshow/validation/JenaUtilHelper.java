package org.researchobject.roshow.validation;

/*
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *  See the NOTICE file distributed with this work for additional
 *  information regarding copyright ownership.
 */

import java.util.Iterator;

import org.apache.jena.graph.Factory;
import org.apache.jena.graph.Graph;
import org.apache.jena.graph.compose.MultiUnion;
import org.apache.jena.mem.GraphMemBase;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;


public class JenaUtilHelper {


    public MultiUnion createMultiUnion() {
        return new MultiUnion();
    }



    public MultiUnion createMultiUnion(Iterator<Graph> graphs) {
        return new MultiUnion(graphs);
    }



    public MultiUnion createMultiUnion(Graph[] graphs) {
        return new MultiUnion(graphs);
    }



    public Graph createDefaultGraph() {
        return Factory.createDefaultGraph();
    }



    public boolean isMemoryGraph(Graph graph) {
        return (graph instanceof GraphMemBase);
    }



    public Model asReadOnlyModel(Model m) {
        return m;
    }


    public Graph asReadOnlyGraph(Graph g) {
        return g;
    }


    public OntModel createOntologyModel(OntModelSpec spec, Model base) {
        return ModelFactory.createOntologyModel(spec, base);
    }


    public Graph createConcurrentGraph() {
        return createDefaultGraph();
    }


    public void setGraphReadOptimization(boolean b) {
    }

    public Graph deepCloneReadOnlyGraph(Graph g) {
        return asReadOnlyGraph(g);
    }
}
