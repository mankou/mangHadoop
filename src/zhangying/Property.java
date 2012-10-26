package zhangying;

import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.util.List;

import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.ChemFile;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.Molecule;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.formula.MolecularFormula;
import org.openscience.cdk.interfaces.IMolecularFormula;
import org.openscience.cdk.io.Mol2Reader;
import org.openscience.cdk.qsar.DescriptorValue;
import org.openscience.cdk.qsar.descriptors.molecular.FragmentComplexityDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.HBondAcceptorCountDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.HBondDonorCountDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.RotatableBondsCountDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.TPSADescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.WeightDescriptor;
import org.openscience.cdk.qsar.descriptors.molecular.XLogPDescriptor;
import org.openscience.cdk.qsar.result.DoubleResult;
import org.openscience.cdk.qsar.result.IntegerResult;
import org.openscience.cdk.smiles.SmilesGenerator;
import org.openscience.cdk.smiles.SmilesParser;
import org.openscience.cdk.tools.manipulator.ChemFileManipulator;
import org.openscience.cdk.tools.manipulator.MolecularFormulaManipulator;

public class Property {
	public String getSmiles() {
		return smiles;
	}
	public void setSmiles(String smiles) {
		this.smiles = smiles;
	}
	public Double getXLogP() {
		return XLogP;
	}
	public void setXLogP(Double logP) {
		XLogP = logP;
	}
	public Integer getRotatableBonds() {
		return RotatableBonds;
	}
	public void setRotatableBonds(Integer rotatableBonds) {
		RotatableBonds = rotatableBonds;
	}
	public Integer getH_Donor1() {
		return H_Donor1;
	}
	public void setH_Donor1(Integer donor1) {
		H_Donor1 = donor1;
	}
	public Integer getHB_Acceptor1() {
		return HB_Acceptor1;
	}
	public void setHB_Acceptor1(Integer acceptor1) {
		HB_Acceptor1 = acceptor1;
	}
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public Double getPolar_surface_area() {
		return Polar_surface_area;
	}
	public void setPolar_surface_area(Double polar_surface_area) {
		Polar_surface_area = polar_surface_area;
	}
	public Double getComplexity() {
		return complexity;
	}
	public void setComplexity(Double complexity) {
		this.complexity = complexity;
	}
	public double getNaturalMass() {
		return naturalMass;
	}
	public void setNaturalMass(double naturalMass) {
		this.naturalMass = naturalMass;
	}
	public int getAtomCount() {
		return atomCount;
	}
	public void setAtomCount(int atomCount) {
		this.atomCount = atomCount;
	}
	public int getBondCount() {
		return bondCount;
	}
	public void setBondCount(int bondCount) {
		this.bondCount = bondCount;
	}
	public double getMolecule_weight() {
		return molecule_weight;
	}
	public void setMolecule_weight(double molecule_weight) {
		this.molecule_weight = molecule_weight;
	}
	private String smiles;
	private double XLogP;
	private int RotatableBonds;
	private int H_Donor1;
	private int HB_Acceptor1;
	private String formula;
	private double Polar_surface_area;
	private double complexity;
	private double naturalMass;
	private int atomCount;
	private int bondCount;
	private double molecule_weight;
	public Property(){}
	public Property(String str) throws CDKException{
		Mol2Reader mol2Reader;
		ChemFileManipulator chemM = new ChemFileManipulator();//�ṩת������
		AtomContainer atomContainer = new AtomContainer();//���л�ѧ����Ļ���
		
		XLogPDescriptor xLogP = new XLogPDescriptor();//Ԥ��logp��ֵ
		DescriptorValue desValue ;//�洢���ӵ�����ֵ
		RotatableBondsCountDescriptor rotatableBonds = new RotatableBondsCountDescriptor();
		HBondDonorCountDescriptor H_Donor = new HBondDonorCountDescriptor(); 
		HBondAcceptorCountDescriptor HB_Acceptor = new HBondAcceptorCountDescriptor(); 
		InputStream input=new StringBufferInputStream(str);  
		 mol2Reader = new Mol2Reader(input);//��mol2�ļ��ж�ȡһ������
		ChemFile chemFile2 = (ChemFile)mol2Reader.read(new ChemFile());//��������
        List list = chemM.getAllAtomContainers(chemFile2);
        atomContainer = (AtomContainer)list.get(0);
        
        // calculate XLogP value
        SmilesParser sp = new SmilesParser(DefaultChemObjectBuilder.getInstance());
        SmilesGenerator smilesGen =  new SmilesGenerator();
        Molecule mol = new Molecule(atomContainer);
         smiles = smilesGen.createSMILES(mol);
//o			        System.out.println("Smiles:  "+smiles);
//o			        System.out.println("HashCode = :  "+smiles.hashCode());
//        Molecule molecule = (Molecule) sp.parseSmiles(smiles);
//        System.out.println("Molecule:  "+molecule.toString());
//        IAtomContainer ac = 
        desValue = xLogP.calculate(atomContainer);
        DoubleResult res = (DoubleResult)desValue.getValue();
//        if(res.doubleValue()==(double)(NaN))
         XLogP = (double)res.doubleValue();
//o			        System.out.println("XLogP:  "+(double)res.doubleValue());
        
       // calculate rotatable bond value
        desValue = rotatableBonds.calculate(atomContainer);
        IntegerResult inres = (IntegerResult)desValue.getValue();
         RotatableBonds=inres.intValue();
//o			        System.out.println("RotatableBonds:  "+inres.intValue());
        
     // calculate # H-donor value
        desValue = H_Donor.calculate(atomContainer);
        inres = (IntegerResult)desValue.getValue();
         H_Donor1 = inres.intValue();
//o			        System.out.println("H_Donor:  "+inres.intValue());
        
     // calculate # H-acceptor value
        desValue = HB_Acceptor.calculate(atomContainer);
        inres = (IntegerResult)desValue.getValue();
         HB_Acceptor1 = inres.intValue();
//o			        System.out.println("HB_Acceptor:  "+inres.intValue());
      
      // calculate molecular formula
        IMolecularFormula moleculeFormula = MolecularFormulaManipulator.getMolecularFormula(atomContainer, new MolecularFormula());
         formula = MolecularFormulaManipulator.getString(moleculeFormula);
//o			        System.out.println("MolecularFormula:  "+formula);
        
//        SmilesGenerator smilesGen =  new SmilesGenerator();
//        Molecule mol = new Molecule(atomContainer);
//        String smiles = smilesGen.createSMILES(mol);
//        System.out.println("Smiles:  "+smiles);
//        System.out.println("HashCode = :  "+smiles.hashCode());
        
      // calculate Polar surface area
        TPSADescriptor tpsad = new TPSADescriptor(); 
        desValue = tpsad.calculate(atomContainer);
        res = (DoubleResult)desValue.getValue();
         Polar_surface_area = res.doubleValue();
//o			        System.out.println("Polar surface area:  "+res.doubleValue());
      
      // calculate the complexity of a system
        FragmentComplexityDescriptor fcd = new FragmentComplexityDescriptor();
        desValue = fcd.calculate(atomContainer);
        res = (DoubleResult)desValue.getValue();
         complexity = res.doubleValue();
//o			        System.out.println("complexity:  "+res.doubleValue());
       // calculate natural mass
         naturalMass = MolecularFormulaManipulator.getNaturalExactMass(moleculeFormula);
//o			        System.out.println("Natural Mass:  "+naturalMass);
        
         atomCount = chemM.getAtomCount(chemFile2);
//o			        System.out.println("AtomCount:  "+atomCount);
         bondCount = chemM.getBondCount(chemFile2);
//o			        System.out.println("BondCount:  "+bondCount);
         
         WeightDescriptor moleculeWeight = new WeightDescriptor();
         desValue = moleculeWeight.calculate(atomContainer);
         res = (DoubleResult)desValue.getValue();
         molecule_weight = res.doubleValue();
//         System.out.println("Molecule Weight:  "+res.doubleValue());
	}
}
