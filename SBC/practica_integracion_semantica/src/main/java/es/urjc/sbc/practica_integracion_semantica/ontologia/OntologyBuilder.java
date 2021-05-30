package es.urjc.sbc.practica_integracion_semantica.ontologia;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

public class OntologyBuilder {
	private OWLOntologyManager manager;
	private OWLDataFactory factory;
	private OWLOntology ontology;
	private DefaultPrefixManager prefixManager;

	public OntologyBuilder() {
		this("http://sbc2021.es/integracion_semantica");
	}

	public OntologyBuilder(String url) {
		manager = OWLManager.createOWLOntologyManager();
		factory = manager.getOWLDataFactory();
		setBaseIRI(url);
	}

	public OntologyBuilder(OWLOntology onto) {
		manager = OWLManager.createOWLOntologyManager();
		factory = manager.getOWLDataFactory();
		ontology = onto;
		IRI baseIRI = onto.getOntologyID().getOntologyIRI().get();
		prefixManager = new DefaultPrefixManager(baseIRI.getIRIString());
	}

	public void setBaseIRI(String url) {
		IRI baseIRI = IRI.create(url);
		prefixManager = new DefaultPrefixManager(baseIRI.getIRIString());
	}

	public OWLOntology getOntology() {
		return ontology;
	}

	public void saveOntology(String filename) {
		IRI fileIRI = IRI.create(new File(filename));
		try {
			// manager.saveOntology(ontology, fileIRI);
			ontology.saveOntology(fileIRI);
		} catch (OWLOntologyStorageException e) {
			throw new RuntimeException(e);
		}
	}

	public void addClass(String className) {
		String[] aux = className.split(":", 2);
		boolean hasPrefix = (aux.length > 1);

		// create IRI
		String baseIRI = prefixManager.getDefaultPrefix();
		String url = hasPrefix ? prefixManager.getPrefix(aux[0] + ":") + aux[1] : baseIRI + "#" + className;
		IRI classIRI = IRI.create(url);

		// add class
		OWLClass newClass = factory.getOWLClass(classIRI);
		OWLDeclarationAxiom axioma = factory.getOWLDeclarationAxiom(newClass);
		manager.addAxiom(ontology, axioma);
	}

	public void addSubClass(String subClassName, String parentClassName) {
		String[] aux1 = subClassName.split(":", 2);
		String[] aux2 = parentClassName.split(":", 2);

		// get prefix
		boolean hasPrefix1 = (aux1.length > 1);
		boolean hasPrefix2 = (aux2.length > 1);

		String baseIRI = prefixManager.getDefaultPrefix();
		String subClassPrefix = hasPrefix1 ? prefixManager.getPrefix(aux1[0] + ":") : baseIRI;
		String parClassPrefix = hasPrefix2 ? prefixManager.getPrefix(aux2[0] + ":") : baseIRI;

		// create iri
		String subClassURL = hasPrefix1 ? subClassPrefix + aux1[1] : baseIRI + "#" + subClassName;
		String parClassURL = hasPrefix2 ? parClassPrefix + aux2[1] : baseIRI + "#" + parentClassName;
		IRI subClassIRI = IRI.create(subClassURL);
		IRI parClassIRI = IRI.create(parClassURL);

		// add subclass
		OWLClass subClass = factory.getOWLClass(subClassIRI);
		OWLClass parentClass = factory.getOWLClass(parClassIRI);
		OWLSubClassOfAxiom axioma = factory.getOWLSubClassOfAxiom(subClass, parentClass);
		manager.addAxiom(ontology, axioma);
	}

	public void addObjectProperty(String property, String domain, String range) {
		// get prefix
		String[] aux1 = property.split(":", 2);
		String[] aux2 = domain.split(":", 2);
		String[] aux3 = range.split(":", 2);

		boolean hasPrefix1 = (aux1.length > 1);
		boolean hasPrefix2 = (aux2.length > 1);
		boolean hasPrefix3 = (aux3.length > 1);

		// create iri
		String baseIRI = prefixManager.getDefaultPrefix();
		String pPrefix = hasPrefix1 ? prefixManager.getPrefix(aux1[0] + ":") : baseIRI;
		String dPrefix = hasPrefix2 ? prefixManager.getPrefix(aux2[0] + ":") : baseIRI;
		String rPrefix = hasPrefix3 ? prefixManager.getPrefix(aux3[0] + ":") : baseIRI;

		String pURL = hasPrefix1 ? pPrefix + aux1[1] : pPrefix + "#" + property;
		String dURL = hasPrefix2 ? dPrefix + aux2[1] : dPrefix + "#" + domain;
		String rURL = hasPrefix3 ? rPrefix + aux3[1] : rPrefix + "#" + range;

		IRI pIRI = IRI.create(pURL);
		IRI dIRI = IRI.create(dURL);
		IRI rIRI = IRI.create(rURL);

		// add Property domain and range
		OWLObjectProperty dataProperty = factory.getOWLObjectProperty(pIRI);
		OWLClass domClass = factory.getOWLClass(dIRI);
		OWLClass rangeClass = factory.getOWLClass(rIRI);

		OWLObjectPropertyDomainAxiom domainAxioma = factory
				.getOWLObjectPropertyDomainAxiom(dataProperty.asObjectPropertyExpression(), domClass);
		OWLObjectPropertyRangeAxiom rangeAxioma = factory
				.getOWLObjectPropertyRangeAxiom(dataProperty.asObjectPropertyExpression(), rangeClass);
		manager.addAxiom(ontology, domainAxioma);
		manager.addAxiom(ontology, rangeAxioma);
	}

	public void addDataProperty(String property, OWLDatatype type) {
		// get prefix
		String[] aux1 = property.split(":", 2);
		boolean hasPrefix1 = (aux1.length > 1);

		// create iri
		String baseIRI = prefixManager.getDefaultPrefix();
		String dpPrefix = hasPrefix1 ? prefixManager.getPrefix(aux1[0] + ":") : baseIRI;
		String dpURL = hasPrefix1 ? dpPrefix + aux1[1] : dpPrefix + "#" + property;
		IRI dataIRI = IRI.create(dpURL);

		// add data property and range
		OWLDataProperty dataProperty = factory.getOWLDataProperty(dataIRI);
		OWLDataPropertyRangeAxiom rangeAxioma = factory
				.getOWLDataPropertyRangeAxiom(dataProperty.asDataPropertyExpression(), type);
		manager.addAxiom(ontology, rangeAxioma);
	}

	public void addEquivalence(String className, String property, String domain) {
		// get prefix
		String[] aux1 = className.split(":", 2);
		String[] aux2 = property.split(":", 2);
		String[] aux3 = domain.split(":", 2);

		boolean hasPrefix1 = (aux1.length > 1);
		boolean hasPrefix2 = (aux2.length > 1);
		boolean hasPrefix3 = (aux3.length > 1);

		// create IRIs
		String baseIRI = prefixManager.getDefaultPrefix();
		String cPrefix = hasPrefix1 ? prefixManager.getPrefix(aux1[0] + ":") : baseIRI;
		String pPrefix = hasPrefix2 ? prefixManager.getPrefix(aux2[0] + ":") : baseIRI;
		String dPrefix = hasPrefix3 ? prefixManager.getPrefix(aux3[0] + ":") : baseIRI;

		String cURL = hasPrefix1 ? cPrefix + aux1[1] : cPrefix + "#" + className;
		String pURL = hasPrefix2 ? pPrefix + aux2[1] : pPrefix + "#" + property;
		String dURL = hasPrefix3 ? dPrefix + aux3[1] : dPrefix + "#" + domain;

		IRI classIRI = IRI.create(cURL);
		IRI propertyIRI = IRI.create(pURL);
		IRI domainIRI = IRI.create(dURL);

		// add equivalence
		OWLClass baseClass = factory.getOWLClass(classIRI);
		OWLClass domainClass = factory.getOWLClass(domainIRI);

		OWLObjectProperty dataProperty = factory.getOWLObjectProperty(propertyIRI);
		OWLObjectSomeValuesFrom relation = factory.getOWLObjectSomeValuesFrom(dataProperty, domainClass);

		OWLEquivalentClassesAxiom axioma = factory.getOWLEquivalentClassesAxiom(baseClass, relation);
		manager.addAxiom(ontology, axioma);
	}

	public void addInclusion(String className, String property, String domain) {
		// get prefix
		String[] aux1 = className.split(":", 2);
		String[] aux2 = property.split(":", 2);
		String[] aux3 = domain.split(":", 2);

		boolean hasPrefix1 = (aux1.length > 1);
		boolean hasPrefix2 = (aux2.length > 1);
		boolean hasPrefix3 = (aux3.length > 1);

		// create iri
		String baseIRI = prefixManager.getDefaultPrefix();
		String cPrefix = hasPrefix1 ? prefixManager.getPrefix(aux1[0] + ":") : baseIRI;
		String pPrefix = hasPrefix2 ? prefixManager.getPrefix(aux2[0] + ":") : baseIRI;
		String dPrefix = hasPrefix3 ? prefixManager.getPrefix(aux3[0] + ":") : baseIRI;

		String cURL = hasPrefix1 ? cPrefix + aux1[1] : cPrefix + "#" + className;
		String pURL = hasPrefix2 ? pPrefix + aux2[1] : pPrefix + "#" + property;
		String dURL = hasPrefix3 ? dPrefix + aux3[1] : dPrefix + "#" + domain;

		IRI classIRI = IRI.create(cURL);
		IRI propertyIRI = IRI.create(pURL);
		IRI domainIRI = IRI.create(dURL);

		// add inclusion
		OWLClass baseClass = factory.getOWLClass(classIRI);
		OWLClass domainClass = factory.getOWLClass(domainIRI);
		OWLObjectProperty dataProperty = factory.getOWLObjectProperty(propertyIRI);

		OWLObjectSomeValuesFrom relation = factory.getOWLObjectSomeValuesFrom(dataProperty, domainClass);
		OWLSubClassOfAxiom subClassAxioma = factory.getOWLSubClassOfAxiom(baseClass, relation);

		manager.addAxiom(ontology, subClassAxioma);
	}

	public void addIndividualToClass(String name, String className) {
		// get prefix
		String[] aux1 = name.split(":", 2);
		String[] aux2 = className.split(":", 2);

		boolean hasPrefix1 = (aux1.length > 1);
		boolean hasPrefix2 = (aux2.length > 1);

		// create IRI
		String baseIRI = prefixManager.getDefaultPrefix();
		String iPrefix = hasPrefix1 ? prefixManager.getPrefix(aux1[0] + ":") : baseIRI;
		String cPrefix = hasPrefix2 ? prefixManager.getPrefix(aux2[0] + ":") : baseIRI;

		String iURL = hasPrefix1 ? iPrefix + aux1[1] : iPrefix + "#" + name;
		String cURL = hasPrefix2 ? cPrefix + aux2[1] : cPrefix + "#" + className;

		IRI individualIRI = IRI.create(iURL);
		IRI classIRI = IRI.create(cURL);

		// add individual
		OWLNamedIndividual individual = factory.getOWLNamedIndividual(individualIRI);
		OWLClass instanceClass = factory.getOWLClass(classIRI);
		OWLClassAssertionAxiom axioma = factory.getOWLClassAssertionAxiom(instanceClass, individual);
		manager.addAxiom(ontology, axioma);
	}

	public void addPrefix(String shortcut, String fullform) {
		prefixManager.setPrefix(shortcut, fullform);
	}

	public void addSentence(String subject, String predicate, String object) {
		// get prefix
		String[] aux1 = subject.split(":", 2);
		String[] aux2 = predicate.split(":", 2);
		String[] aux3 = object.split(":", 2);

		boolean hasPrefix1 = (aux1.length > 1);
		boolean hasPrefix2 = (aux2.length > 1);
		boolean hasPrefix3 = (aux3.length > 1);

		String baseIRI = prefixManager.getDefaultPrefix();
		String subPrefix = hasPrefix1 ? prefixManager.getPrefix(aux1[0] + ":") : baseIRI;
		String prePrefix = hasPrefix2 ? prefixManager.getPrefix(aux2[0] + ":") : baseIRI;
		String objPrefix = hasPrefix3 ? prefixManager.getPrefix(aux3[0] + ":") : baseIRI;

		// create iri
		String sIri = hasPrefix1 ? subPrefix + aux1[1] : subPrefix + "#" + subject;
		String pIri = hasPrefix2 ? prePrefix + aux2[1] : prePrefix + "#" + predicate;
		String oIri = hasPrefix3 ? objPrefix + aux3[1] : objPrefix + "#" + object;

		// add object sentence
		OWLIndividual subIndividual = factory.getOWLNamedIndividual(sIri);
		OWLObjectProperty property = factory.getOWLObjectProperty(pIri);
		OWLIndividual objIndividual = object.isEmpty() ? factory.getOWLAnonymousIndividual()
				: factory.getOWLNamedIndividual(oIri);

		OWLObjectPropertyAssertionAxiom axioma = factory.getOWLObjectPropertyAssertionAxiom(property, subIndividual,
				objIndividual);
		manager.addAxiom(ontology, axioma);
	}

	public void addData(String subject, String property, String object) {
		// get prefix
		String[] aux1 = subject.split(":", 2);
		String[] aux2 = property.split(":", 2);

		boolean hasPrefix1 = (aux1.length > 1);
		boolean hasPrefix2 = (aux2.length > 1);

		String baseIRI = prefixManager.getDefaultPrefix();
		String subPrefix = hasPrefix1 ? prefixManager.getPrefix(aux1[0] + ":") : baseIRI;
		String prePrefix = hasPrefix2 ? prefixManager.getPrefix(aux2[0] + ":") : baseIRI;

		// create iri
		String sIri = hasPrefix1 ? subPrefix + aux1[1] : subPrefix + "#" + subject;
		String pIri = hasPrefix2 ? prePrefix + aux2[1] : prePrefix + "#" + property;

		// add data sentence
		OWLIndividual subIndividual = factory.getOWLNamedIndividual(sIri);
		OWLDataProperty dataProperty = factory.getOWLDataProperty(pIri);
		OWLLiteral literal = factory.getOWLLiteral(object);

		OWLDataPropertyAssertionAxiom axioma = factory.getOWLDataPropertyAssertionAxiom(dataProperty, subIndividual,
				literal);
		manager.addAxiom(ontology, axioma);
	}
}
