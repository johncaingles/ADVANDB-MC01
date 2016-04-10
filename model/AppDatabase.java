package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import utilities.dbconnection.MySQLConnector;

public class AppDatabase {

    public static final int CENTRAL_NODE_ID = 0;
    public static final int MARINDUQUE_NODE_ID = 2;
	public static final int PALAWAN_NODE_ID = 1;

    public static final int CENTRAL_PORT_NUMBER = 1234;
    public static final int MARINDUQUE_PORT_NUMBER = 1233;
    public static final int PALAWAN_PORT_NUMBER = 1235;

    public static String CENTRAL_IP_ADDRESS    = "localhost";
    public static String MARINDUQUE_IP_ADDRESS = "localhost";
    public static String PALAWAN_IP_ADDRESS    = "localhost";


    private LinkedHashMap<String, LinkedHashMap<String, String>> queryStatements;
	private LinkedHashMap<String, String> queryInfos;
	
	public AppDatabase(){
		queryStatements = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		queryInfos = new LinkedHashMap<String, String>();
		initializeQueryOptimizations();
		intializeQueryInfos();
	}
	

	private void initializeQueryOptimizations() {
		LinkedHashMap<String, String> queryStatementsBasedOnOptimization;
		String queryType;
		String queryDescription;
		String optimizationType;
		String statement;
		
		/** Custom */
		queryType = "Custom";
		/** Description */
		queryDescription = "Enter anything bebeh!";
		/** Start of Optimization List */
		queryStatementsBasedOnOptimization = new LinkedHashMap<>();
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 1 */
		queryType = "Query 1 (1 Table)";
		/** Description */
		queryDescription = "Ooooh look at me I am the first query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Original";
			statement = "select  id, cropind, cropincsh, cropinknd , cropincsh + cropinknd as Total from hpq_hh \n"
					+ "where cropind = 1 \n"
					+ "order by total desc";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Add index";
			statement = "ALTER TABLE `db_hpq`.`hpq_hh` "
					+ "\nADD INDEX `Index` (`cropind` ASC);";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Drop index";
			statement = "ALTER TABLE `db_hpq`.`hpq_hh` "
					+ "\nDROP INDEX `Index` ";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Stored Procedure";
			statement = "DELIMITER //"
					+ "\nCREATE PROCEDURE Query1()"
					+ "\nBEGIN"
					+ "\nselect  id, cropind, cropincsh, cropinknd , cropincsh + cropinknd as Total from hpq_hh"
					+ "\nwhere cropind = 1"
					+ "\norder by total desc;"
					+ "\nEND //"
					+ "\nCALL Query1();";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 2 */
		queryType = "Query 2 (1 Table)";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Original";
			statement = "SELECT water, count(id) from hpq_hh"
					+ "\ngroup by water";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Add index";
			statement = "ALTER TABLE `db_hpq`.`hpq_hh` "
					+ "\nADD INDEX `Index` (`water` ASC);";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);

			optimizationType = "Drop index";
			statement = "ALTER TABLE `db_hpq`.`hpq_hh` "
					+ "\nDROP INDEX `Index`";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Stored Procedure";
			statement = "DELIMITER //"
					+ "\nCREATE PROCEDURE Query2()"
					+ "\nBEGIN"
					+ "\nSELECT water, count(id) from hpq_hh"
					+ "\ngroup by water;"
					+ "\nEND //"
					+ "\nCALL Query2();";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 3 */
		queryType = "Query 3 (2 Tables)";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Original";
			statement = "SELECT id, sum(wagcshm) , max(memno) person, sum(wagcshm)/max(memno) cashperperson "
					+ "\nFROM db_hpq.hpq_mem"
					+ "\nwhere id in (select hpq_hh_id from hpq_cbep_mem)"
					+ "\ngroup by id";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);

			optimizationType = "Add index";
			statement = "ALTER TABLE `db_hpq`.`hpq_cbep_mem`"
					+ "\nADD INDEX `Index` (`hpq_hh_id` ASC);";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Drop index";
			statement = "ALTER TABLE `db_hpq`.`hpq_cbep_mem` "
					+ "\nDROP INDEX `Index` ;";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Stored Procedure";
			statement = "DELIMITER //CREATE PROCEDURE Query3()BEGINSELECT id, sum(wagcshm) , max(memno) person, sum(wagcshm)/max(memno) cashperperson FROM db_hpq.hpq_memwhere id in (select hpq_hh_id from hpq_cbep_mem)group by id;END //CALL Query3();";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 4 */
		queryType = "Query 4(2 Tables)";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Original";
			statement = "SELECT gradel AS Grade_level, COUNT(*) AS Amount_of_goods"
					+ "\nFROM hpq_mem"
					+ "\nINNER JOIN hpq_fudforsch_mem"
					+ "\non hpq_mem.id = hpq_fudforsch_mem.hpq_hh_id"
					+ "\nWHERE gradel IS NOT NULL"
					+ "\nGROUP BY gradel";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Add index";
			statement = "ALTER TABLE `db_hpq`.`hpq_fudforsch_mem` "
					+ "\nADD INDEX `Index` (`hpq_hh_id` ASC);";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Drop index";
			statement = "ALTER TABLE `db_hpq`.`hpq_fudforsch_mem` "
					+ "\nDROP INDEX `Index` ;";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Stored Procedure";
			statement = "DELIMITER //CREATE PROCEDURE Query4()BEGINSELECT gradel AS Grade_level, COUNT(*) AS Amount_of_goodsFROM hpq_memINNER JOIN hpq_fudforsch_memon hpq_mem.id = hpq_fudforsch_mem.hpq_hh_idWHERE gradel IS NOT NULLGROUP BY gradel;END //CALL Query4();";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Heuristics";
			statement = "SELECT t1.gradel, COUNT(*) AS Amount_of_goods"
					+ "\nFROM (SELECT id, gradel "
					+ "\nFROM hpq_mem"
					+ "\nWHERE gradel IS NOT NULL) t1"
					+ "\nNATURAL JOIN"
					+ "\n(SELECT hpq_hh_id AS id"
					+ "\nFROM hpq_fudforsch_mem) t2"
					+ "\nGROUP BY t1.gradel";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 5 */
		queryType = "Query 5(2 Tables)";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Original";
			statement = "select id from hpq_hh"
					+ "\nwhere id in (select hpq_hh_id from hpq_cshforwrk_mem) AND "
					+ "\nid in (select hpq_hh_id from hpq_fudforwrk_mem)";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Add index";
			statement = "ALTER TABLE `db_hpq`.`hpq_cshforwrk_mem` "
					+ "\nADD INDEX `Index` (`hpq_hh_id` ASC);\n"
					+ "\nALTER TABLE `db_hpq`.`hpq_fudforwrk_mem` "
					+ "\nADD INDEX `Index` (`hpq_hh_id` ASC);";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Drop index";
			statement = "ALTER TABLE `db_hpq`.`hpq_cshforwrk_mem` "
					+ "\nDROP INDEX `Index` ;"
					+ "\nALTER TABLE `db_hpq`.`hpq_fudforwrk_mem` "
					+ "\nDROP INDEX `Index` ;";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Heuristics";
			statement = "select DISTINCT *"
					+ "\nfrom (select hpq_hh_id AS id"
					+ "\nfrom hpq_cshforwrk_mem) t1"
					+ "\nNATURAL JOIN"
					+ "\n(select hpq_hh_id AS id"
					+ "\nfrom hpq_fudforwrk_mem) t2";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Stored Procedure";
			statement = "DELIMITER //CREATE PROCEDURE Query5()BEGINselect id from hpq_hhwhere id in (select hpq_hh_id from hpq_cshforwrk_mem) AND id in (select hpq_hh_id from hpq_fudforwrk_mem);END //CALL Query5();";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 6 */
		queryType = "Query 6 (3 Tables)";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Original";
			statement = "select  H.id, fishind, fishincsh, fishinknd , fishincsh + fishinknd as Total , A.aquanitype, A.aquanitype_o,E.aquaequiptype, E.aquaequiptype_o "
					+ "\nfrom hpq_hh H, hpq_aquani A, hpq_aquaequip E"
					+ "\nwhere fishind = 1 and H.id=A.hpq_hh_id and H.id=E.hpq_hh_id"
					+ "\norder by total desc";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Add index";
			statement = "ALTER TABLE `db_hpq`.`hpq_hh` "
					+ "\nADD INDEX `Index` (`fishind` ASC, `id` ASC);"
					+ "\nALTER TABLE `db_hpq`.`hpq_aquani` "
					+ "\nADD INDEX `Index` (`hpq_hh_id` ASC);"
					+ "\nALTER TABLE `db_hpq`.`hpq_aquaequip` "
					+ "\nADD INDEX `Index` (`hpq_hh_id` ASC);";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Drop index";
			statement = "ALTER TABLE `db_hpq`.`hpq_hh` "
					+ "\nDROP INDEX `Index` ;"
					+ "\nALTER TABLE `db_hpq`.`hpq_aquani` "
					+ "\nDROP INDEX `Index` ;"
					+ "\nALTER TABLE `db_hpq`.`hpq_aquaequip`"
					+ "\nDROP INDEX `Index` ;";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Original";
			statement = "SELECT * FROM("
					+ "\nSELECT id, fishind, fishincsh, fishinknd, fishincsh + fishinknd AS Total"
					+ "\nFROM hpq_hh"
					+ "\nWHERE fishind = 1"
					+ "\n) t1"
					+ "\nNATURAL JOIN"
					+ "\n(SELECT hpq_hh_id AS id, aquanitype, aquanitype_o"
					+ "\nFROM hpq_aquani) t2"
					+ "\nNATURAL JOIN"
					+ "\n(SELECT hpq_hh_id AS id, aquaequiptype, aquaequiptype_o"
					+ "\nFROM hpq_aquaequip) t3"
					+ "\norder by total desc";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Stored Procedure";
			statement = "DELIMITER //CREATE PROCEDURE Query6()BEGINselect  H.id, fishind, fishincsh, fishinknd , fishincsh + fishinknd as Total , A.aquanitype, A.aquanitype_o,E.aquaequiptype, E.aquaequiptype_o from hpq_hh H, hpq_aquani A, hpq_aquaequip Ewhere fishind = 1 and H.id=A.hpq_hh_id and H.id=E.hpq_hh_idorder by total desc;END //CALL Query6();";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
		
		/** Query 7 */
		queryType = "Query 7(5 Tables)";
		/** Description */
		queryDescription = "Ooooh look at me I am the second query po";
		/** Start of Optimization List */
			queryStatementsBasedOnOptimization = new LinkedHashMap<>();
			
			optimizationType = "Original";
			statement = "SELECT id, memno, msname, mfname, mmname FROM db_hpq.hpq_mem"
					+ "\nWHERE id NOT IN (SELECT hpq_hh_id from hpq_phiheal_empl_mem) AND "
					+ "\nid NOT IN (SELECT hpq_hh_id from hpq_phiheal_indiv_mem) AND"
					+ "\nid NOT IN (SELECT hpq_hh_id from hpq_phiheal_life_mem) AND"
					+ "\nid NOT IN (SELECT hpq_hh_id from hpq_phiheal_ofw_mem) ";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Add index";
			statement = "ALTER TABLE `db_hpq`.`hpq_phiheal_empl_mem` "
					+ "\nADD INDEX `Index` (`hpq_hh_id` ASC);"
					+ "\nALTER TABLE `db_hpq`.`hpq_phiheal_indiv_mem` "
					+ "\nADD INDEX `Index` (`hpq_hh_id` ASC);"
					+ "\nALTER TABLE `db_hpq`.`hpq_phiheal_life_mem` "
					+ "\nADD INDEX `Index` (`hpq_hh_id` ASC);"
					+ "\nALTER TABLE `db_hpq`.`hpq_phiheal_ofw_mem` "
					+ "\nADD INDEX `Index` (`hpq_hh_id` ASC);";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Drop index";
			statement = "ALTER TABLE `db_hpq`.`hpq_phiheal_empl_mem` "
					+ "\nDROP INDEX `Index` ;"
					+ "\nALTER TABLE `db_hpq`.`hpq_phiheal_indiv_mem` "
					+ "\nDROP INDEX `Index` ;"
					+ "\nALTER TABLE `db_hpq`.`hpq_phiheal_life_mem`"
					+ "\nDROP INDEX `Index` ;"
					+ "\nALTER TABLE `db_hpq`.`hpq_phiheal_ofw_mem` "
					+ "\nDROP INDEX `Index` ;";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
			optimizationType = "Stored Procedure";
			statement = "DELIMITER //CREATE PROCEDURE Query7()BEGINSELECT id, memno, msname, mfname, mmname FROM db_hpq.hpq_memWHERE id NOT IN (SELECT hpq_hh_id from hpq_phiheal_empl_mem) AND id NOT IN (SELECT hpq_hh_id from hpq_phiheal_indiv_mem) AND id NOT IN (SELECT hpq_hh_id from hpq_phiheal_life_mem) AND id NOT IN (SELECT hpq_hh_id from hpq_phiheal_ofw_mem) ;END //CALL Query7();";
			queryStatementsBasedOnOptimization.put(optimizationType, statement);
			
		/** End of Optimization List */
		/** Final initialization */
		queryStatements.put(queryType, queryStatementsBasedOnOptimization);
		queryInfos.put(queryType, queryDescription);
	}

	private void intializeQueryInfos() {
		
	}
	
	public LinkedHashMap<String, LinkedHashMap<String, String>> getMapOfQueries(){
		return queryStatements;
	}
		
}
