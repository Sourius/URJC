package es.urjc.sbc.ReasonerTutorial;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
//import org.semanticweb.owlapi.util.DefaultPrefixManager;

/*
 * Ontologias con OWLAPI
 */
public class Ontology {
	private OWLOntologyManager manager;
	private OWLDataFactory factory;
	private IRI baseIRI;
	private OWLOntology ontology;
//	private DefaultPrefixManager prefixManager;
	private String filename;

	public Ontology() {
		this("http://sbc2020.es/onto");
	}

	public Ontology(String url) {
		manager = OWLManager.createOWLOntologyManager();
		factory = manager.getOWLDataFactory();
		setBaseIRI(url);
	}

	public void setBaseIRI(String url) {
		baseIRI = IRI.create(url);
//		prefixManager = new DefaultPrefixManager(baseIRI.getIRIString());
	}

	public OWLOntology getOntology() {
		return ontology;
	}

	public void generateOntology(String filename) {
		try {
			this.filename = filename;
			ontology = manager.createOntology(baseIRI);
			saveOntology();
		} catch (OWLOntologyCreationException e) {
			System.out.println("Create Ontology failed.");
			e.printStackTrace();
		}
	}
	
	public void saveOntology() {
		IRI fileIRI = IRI.create(new File(filename));
		try {
			manager.saveOntology(ontology, fileIRI);
		} catch (OWLOntologyStorageException e) {
			System.out.println("Save Ontology failed");
			e.printStackTrace();
		}
	}
	
	public void addClass(String className) {
		// create iri
		IRI classIRI = IRI.create(baseIRI.getIRIString() + "#" + className);
		// get class
		OWLClass newClass = factory.getOWLClass(classIRI);
		// get axiom
		OWLDeclarationAxiom axioma = factory.getOWLDeclarationAxiom(newClass);
		// add axiom
		manager.addAxiom(ontology, axioma);
		// save
		saveOntology();
	}

	public void addSubClass(String subClassName, String parentClassName) {
		// get class
		IRI subClassIRI = IRI.create(baseIRI.getIRIString() + "#" + subClassName);
		OWLClass subClass = factory.getOWLClass(subClassIRI);
		IRI classIRI = IRI.create(baseIRI.getIRIString() + "#" + parentClassName);
		OWLClass parentClass = factory.getOWLClass(classIRI);
		// get subclass
		OWLSubClassOfAxiom axioma = factory.getOWLSubClassOfAxiom(subClass, parentClass);
		// add axiom
		manager.addAxiom(ontology, axioma);
		// save
		saveOntology();
	}
	
	public void addObjectProperty(String name, String domain, String range) {
		IRI iri = IRI.create(baseIRI + "#" + name);
		OWLObjectProperty dataProperty = factory.getOWLObjectProperty(iri);
		// create domain
		IRI domIRI = IRI.create(baseIRI + "#" + domain);
		OWLClass domClass = factory.getOWLClass(domIRI);
		// create range
		IRI rangeIRI = IRI.create(baseIRI + "#" + range);
		OWLClass rangeClass = factory.getOWLClass(rangeIRI);
		// get domain and range axioms
		OWLObjectPropertyDomainAxiom domainAxioma = factory
				.getOWLObjectPropertyDomainAxiom(dataProperty.asObjectPropertyExpression(), domClass);
		OWLObjectPropertyRangeAxiom rangeAxioma = factory
				.getOWLObjectPropertyRangeAxiom(dataProperty.asObjectPropertyExpression(), rangeClass);
		// add domain and range axioms
		manager.addAxiom(ontology, domainAxioma);
		manager.addAxiom(ontology, rangeAxioma);
		// save
		saveOntology();
	}
	
	public void addDataProperty(String name, OWLDatatype type) {
		// create IRI
		IRI dataIRI = IRI.create(baseIRI + "#" + name);
		// get data property and range
		OWLDataProperty dataProperty = factory.getOWLDataProperty(dataIRI);
		// get axiom
		OWLDataPropertyRangeAxiom rangeAxioma = factory
				.getOWLDataPropertyRangeAxiom(dataProperty.asDataPropertyExpression(), type);
		// add axiom
		manager.addAxiom(ontology, rangeAxioma);
		// save ontology
		saveOntology();
	}

	public void addEquivalence(String className, String property, String domain) {
		// create iri
		IRI classIRI = IRI.create(baseIRI.getIRIString() + "#" + className);
		IRI propertyIRI = IRI.create(baseIRI.getIRIString() + "#" + property);
		IRI domainIRI = IRI.create(baseIRI.getIRIString() + "#" + domain);
		// get class
		OWLClass baseClass = factory.getOWLClass(classIRI);
		OWLClass domainClass = factory.getOWLClass(domainIRI);
		// get property and relation
		OWLObjectProperty dataProperty = factory.getOWLObjectProperty(propertyIRI);
		OWLObjectSomeValuesFrom relation = factory.getOWLObjectSomeValuesFrom(dataProperty, domainClass);
		// get equivalence axiom
		OWLEquivalentClassesAxiom axioma = factory.getOWLEquivalentClassesAxiom(baseClass, relation);
		// add axiom
		manager.addAxiom(ontology, axioma);
		// save
		saveOntology();
	}

	public void addInclusion(String className, String property, String domain) {
		// create iri
		IRI classIRI = IRI.create(baseIRI.getIRIString() + "#" + className);
		IRI propertyIRI = IRI.create(baseIRI.getIRIString() + "#" + property);
		IRI domainIRI = IRI.create(baseIRI.getIRIString() + "#" + domain);
		// get class
		OWLClass baseClass = factory.getOWLClass(classIRI);
		OWLClass domainClass = factory.getOWLClass(domainIRI);
		// get property and relation
		OWLObjectProperty dataProperty = factory.getOWLObjectProperty(propertyIRI);
		OWLObjectSomeValuesFrom relation = factory.getOWLObjectSomeValuesFrom(dataProperty, domainClass);
		// add subClassAxiom
		OWLSubClassOfAxiom subClassAxioma = factory.getOWLSubClassOfAxiom(baseClass, relation);
		manager.addAxiom(ontology, subClassAxioma);
		// save
		saveOntology();
	}

	public void addIndividual(String name, String className) {
		// create IRI
		IRI individualIRI = IRI.create(baseIRI + "#" + name);
		// get named individual
		OWLNamedIndividual individual = factory.getOWLNamedIndividual(individualIRI);
		// get class
		IRI classIRI = IRI.create(baseIRI + "#" + className);
		OWLClass instanceClass = factory.getOWLClass(classIRI);
		// get axiom
		OWLClassAssertionAxiom axioma = factory.getOWLClassAssertionAxiom(instanceClass, individual);
		// add axiom
		manager.addAxiom(ontology, axioma);
		// save
		saveOntology();
	}
}
