/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.posbo.utility;

import com.dimata.cashierweb.session.masterdata.BillManager;
import com.dimata.cashierweb.session.masterdata.CatalogManager;
import com.dimata.cashierweb.session.masterdata.StockCheckManager;
import com.dimata.cashierweb.session.masterdata.StockCheckMonitor;
import com.dimata.common.db.DBHandler;
import com.dimata.common.entity.contact.ContactList;
import com.dimata.common.entity.contact.PstContactList;
import com.dimata.common.entity.custom.DataCustom;
import com.dimata.common.entity.custom.PstDataCustom;
import com.dimata.common.entity.location.Location;
import com.dimata.common.entity.logger.LogSysHistory;
import com.dimata.common.entity.logger.PstLogSysHistory;
import com.dimata.common.entity.system.PstSystemProperty;
import com.dimata.hanoman.entity.masterdata.MasterType;
import com.dimata.hanoman.entity.masterdata.PstMasterType;
import com.dimata.pos.entity.balance.Balance;
import com.dimata.pos.entity.balance.CashCashier;
import com.dimata.pos.entity.balance.PstBalance;
import com.dimata.pos.entity.balance.PstCashCashier;
import com.dimata.pos.entity.billing.BillMain;
import com.dimata.pos.entity.billing.Billdetail;
import com.dimata.pos.entity.billing.OtherCost;
import com.dimata.pos.entity.billing.PstBillDetail;
import com.dimata.pos.entity.billing.PstBillMain;
import com.dimata.pos.entity.billing.PstOtherCost;
import com.dimata.pos.entity.billing.PstRecipe;
import com.dimata.pos.entity.billing.Recipe;
import com.dimata.pos.entity.masterCashier.CashMaster;
import com.dimata.pos.entity.masterCashier.PstCashMaster;
import com.dimata.pos.entity.payment.CashCreditCard;
import com.dimata.pos.entity.payment.CashCreditPaymentInfo;
import com.dimata.pos.entity.payment.CashCreditPayments;
import com.dimata.pos.entity.payment.CashPayments;
import com.dimata.pos.entity.payment.CashReturn;
import com.dimata.pos.entity.payment.CreditPaymentMain;
import com.dimata.pos.entity.payment.PstCashCreditCard;
import com.dimata.pos.entity.payment.PstCashCreditPayment;
import com.dimata.pos.entity.payment.PstCashCreditPaymentInfo;
import com.dimata.pos.entity.payment.PstCashPayment;
import com.dimata.pos.entity.payment.PstCashReturn;
import com.dimata.pos.entity.payment.PstCreditPaymentMain;
import com.dimata.pos.form.balance.FrmCashCashier;
import com.dimata.pos.form.billing.FrmBillDetail;
import com.dimata.pos.form.billing.FrmBillMain;
import com.dimata.pos.form.masterCashier.FrmCashMaster;
import com.dimata.pos.form.payment.FrmCashCreditCard;
import com.dimata.posbo.entity.masterdata.Category;
import com.dimata.posbo.entity.masterdata.Color;
import com.dimata.posbo.entity.masterdata.MatMappKsg;
import com.dimata.posbo.entity.masterdata.MatMappLocation;
import com.dimata.posbo.entity.masterdata.Material;
import com.dimata.posbo.entity.masterdata.MaterialStock;
import com.dimata.posbo.entity.masterdata.MaterialTypeMapping;
import com.dimata.posbo.entity.masterdata.Merk;
import com.dimata.posbo.entity.masterdata.PriceTypeMapping;
import com.dimata.posbo.entity.masterdata.PstCategory;
import com.dimata.posbo.entity.masterdata.PstColor;
import com.dimata.posbo.entity.masterdata.PstMatMappKsg;
import com.dimata.posbo.entity.masterdata.PstMatMappLocation;
import com.dimata.posbo.entity.masterdata.PstMaterial;
import com.dimata.posbo.entity.masterdata.PstMaterialMappingType;
import com.dimata.posbo.entity.masterdata.PstMaterialStock;
import com.dimata.posbo.entity.masterdata.PstMerk;
import com.dimata.posbo.entity.masterdata.PstPriceTypeMapping;
import com.dimata.posbo.entity.masterdata.PstSales;
import com.dimata.posbo.entity.masterdata.PstSubCategory;
import com.dimata.posbo.entity.masterdata.PstUnit;
import com.dimata.posbo.entity.masterdata.Sales;
import com.dimata.posbo.entity.masterdata.SubCategory;
import com.dimata.posbo.entity.masterdata.Unit;
import com.dimata.posbo.entity.warehouse.MatDispatch;
import com.dimata.posbo.entity.warehouse.MatDispatchItem;
import com.dimata.posbo.entity.warehouse.MatReceive;
import com.dimata.posbo.entity.warehouse.MatReceiveItem;
import com.dimata.posbo.entity.warehouse.PstMatDispatch;
import com.dimata.posbo.entity.warehouse.PstMatDispatchItem;
import com.dimata.posbo.entity.warehouse.PstMatReceive;
import com.dimata.posbo.entity.warehouse.PstMatReceiveItem;
import com.dimata.posbo.form.warehouse.FrmMatDispatch;
import com.dimata.posbo.form.warehouse.FrmMatDispatchItem;
import com.dimata.posbo.form.warehouse.FrmMatReceive;
import com.dimata.posbo.form.warehouse.FrmMatReceiveItem;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author dimata005
 */
public class ServiceStockCheck {
    
    private final String USER_AGENT = "Mozilla/5.0";
    public static String posUrl= PstSystemProperty.getValueByName("PROCHAIN_URL");
    
    private long userId=0;

     public void setUserId(long userId){
         this.userId = userId;
     }
	 
	private static String address = PstSystemProperty.getValueByName("INTEGRASI_DB1"); 
	private static String dataAddress[] = address.split(";");

	private static String dbURL = "";//dataAddress[0];
	private static String username = "";//dataAddress[1];
	private static String password = "";
    
    public void getStock(){
        CashCashier cashCashier = new CashCashier();
        Location location = new Location();
        Vector listOpeningCashier =  PstCashCashier.listCashOpening(0, 0, 
                "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID]+"='"+userId+"' "
                + "AND CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID]+"='1'", "");   
        Vector openingCashier = new Vector(1,1);
        Vector listBillMain = new Vector(1,1);
        if(listOpeningCashier.size() != 0){
            openingCashier = (Vector) listOpeningCashier.get(0);
            if(openingCashier.size() != 0 ){
                cashCashier = (CashCashier) openingCashier.get(0);
                location = (Location) openingCashier.get(2);
            }
        }
        try {
            String url = posUrl+"getStockCard?FRM_FIELD_LOCATION="+location.getOID();
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            //cek status error
            JSONArray jsonarray = new JSONArray(response.toString());
            if (jsonarray.length()>0){
                for (int i=0; i < jsonarray.length(); i++){
                    try {
                        JSONObject jsono = jsonarray.getJSONObject(i);
                        long stockId = jsono.getLong(PstMaterialStock.fieldNames[PstMaterialStock.FLD_MATERIAL_STOCK_ID]);
                        int qty = jsono.getInt("QTY");
                        long materialId = jsono.getLong("MATERIAL_UNIT_ID");
                        try {
                            MaterialStock materialStock = PstMaterialStock.fetchExc(stockId);
                            materialStock.setPeriodeId(jsono.getLong(PstMaterialStock.fieldNames[PstMaterialStock.FLD_PERIODE_ID]));
                            materialStock.setMaterialUnitId(jsono.getLong(PstMaterialStock.fieldNames[PstMaterialStock.FLD_MATERIAL_UNIT_ID]));
                            materialStock.setLocationId(jsono.getLong(PstMaterialStock.fieldNames[PstMaterialStock.FLD_LOCATION_ID]));
                            materialStock.setQty(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_QTY]));
                            materialStock.setQtyMin(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_QTY_MIN]));
                            materialStock.setQtyMax(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_QTY_MAX]));
                            materialStock.setOpeningQty(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_OPENING_QTY]));
                            materialStock.setClosingQty(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_CLOSING_QTY]));
                            materialStock.setQtyIn(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_QTY_IN]));
                            materialStock.setQtyOut(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_QTY_OUT]));
                            materialStock.setQtyOptimum(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_QT_OPTIMUM]));
                            PstMaterialStock.updateExc(materialStock);
                        } catch (Exception exc){
                            MaterialStock materialStock = new MaterialStock();
                            materialStock.setOID(jsono.getLong(PstMaterialStock.fieldNames[PstMaterialStock.FLD_MATERIAL_STOCK_ID]));
                            materialStock.setPeriodeId(jsono.getLong(PstMaterialStock.fieldNames[PstMaterialStock.FLD_PERIODE_ID]));
                            materialStock.setMaterialUnitId(jsono.getLong(PstMaterialStock.fieldNames[PstMaterialStock.FLD_MATERIAL_UNIT_ID]));
                            materialStock.setLocationId(jsono.getLong(PstMaterialStock.fieldNames[PstMaterialStock.FLD_LOCATION_ID]));
                            materialStock.setQty(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_QTY]));
                            materialStock.setQtyMin(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_QTY_MIN]));
                            materialStock.setQtyMax(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_QTY_MAX]));
                            materialStock.setOpeningQty(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_OPENING_QTY]));
                            materialStock.setClosingQty(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_CLOSING_QTY]));
                            materialStock.setQtyIn(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_QTY_IN]));
                            materialStock.setQtyOut(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_QTY_OUT]));
                            materialStock.setQtyOptimum(jsono.getDouble(PstMaterialStock.fieldNames[PstMaterialStock.FLD_QT_OPTIMUM]));
                            PstMaterialStock.insertExcByOid(materialStock);
                        }
                        
                    } catch (Exception exc){}
                }
            }
        } catch (Exception exc){
            
        }
    }
	
	public void sendCashCashier(){
		Date dtNow = new Date();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date dtPrev = cal.getTime();
		String whereCashCashier = PstCashCashier.fieldNames[PstCashCashier.FLD_OPEN_DATE] + " BETWEEN '"
								+ Formater.formatDate(dtPrev, "yyyy-MM-dd") +" 00:00:00' AND '"
								+ Formater.formatDate(dtNow, "yyyy-MM-dd") +" 23:59:00'";
		Vector listCashCashier = PstCashCashier.list(0, 0, whereCashCashier, "");
		if (listCashCashier.size()>0){
			for (int i =0; i < listCashCashier.size(); i++){
				CashCashier cashCashier = (CashCashier) listCashCashier.get(i);
				try {
					
					Calendar openDate = Calendar.getInstance();
                                        if (cashCashier.getOpenDate() != null){
                                            openDate.setTime(cashCashier.getOpenDate());
                                        }
					
                                        
					Calendar closeDate = Calendar.getInstance();
                                        if (cashCashier.getCloseDate() != null){
                                            closeDate.setTime(cashCashier.getCloseDate());
                                        }
					
					URL obj = new URL(posUrl+"/sendSales");
					Map<String,Object> params = new LinkedHashMap<>();
					params.put("FRM_FIELD_DATA_FOR", "cashCashier");
					params.put("command", Command.SAVE);
					params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CASH_CASHIER_ID], cashCashier.getOID());
					params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CASH_MASTER_ID], cashCashier.getCashMasterId());
					params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_APP_USER_ID], cashCashier.getAppUserId());
                                        if (cashCashier.getOpenDate() != null){
                                            params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_OPEN_DATE], cashCashier.getOpenDate().getTime());
                                            params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_OPEN_DATE]+"_yr", openDate.get(Calendar.YEAR));
                                            params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_OPEN_DATE]+"_mn", openDate.get(Calendar.MONTH)+1);
                                            params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_OPEN_DATE]+"_dy", openDate.get(Calendar.DAY_OF_MONTH));
                                            params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_OPEN_DATE]+"_mm", openDate.get(Calendar.MINUTE));
                                            params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_OPEN_DATE]+"_hh", openDate.get(Calendar.HOUR_OF_DAY));
                                        }
					params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SPV_OID], cashCashier.getSpvOid());
					params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SPV_NAME], cashCashier.getSpvName());
					params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SPV_CLOSE_OID], cashCashier.getSpvCloseOid());
					params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SPV_CLOSE_NAME], cashCashier.getSpvCloseName());
					params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_SHIFT_ID], cashCashier.getShiftId());
                                        if (cashCashier.getCloseDate() != null){
                                            params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CLOSE_DATE], cashCashier.getCloseDate().getTime());
                                            params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CLOSE_DATE]+"_yr", closeDate.get(Calendar.YEAR));
                                            params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CLOSE_DATE]+"_mn", closeDate.get(Calendar.MONTH)+1);
                                            params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CLOSE_DATE]+"_dy", closeDate.get(Calendar.DAY_OF_MONTH));
                                            params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CLOSE_DATE]+"_mm", closeDate.get(Calendar.MINUTE));
                                            params.put(FrmCashCashier.fieldNames[FrmCashCashier.FRM_FIELD_CLOSE_DATE]+"_hh", closeDate.get(Calendar.HOUR_OF_DAY));
                                        }
					
					StringBuilder postData = new StringBuilder();
					for (Map.Entry<String,Object> param : params.entrySet()) {
						if (postData.length() != 0) postData.append('&');
						postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
						postData.append('=');
						postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
					}
					byte[] postDataBytes = postData.toString().getBytes("UTF-8");
					
					HttpURLConnection conn = (HttpURLConnection)obj.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
					conn.setDoOutput(true);
					conn.getOutputStream().write(postDataBytes);
					Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					sendCashMaster(cashCashier.getCashMasterId());
					sendCashBalance(cashCashier.getOID());
					sendCashBillMain();
				} catch (Exception exc){
					
				}
					
			}
		}
	}
	
	public void sendCashMaster(long oidCashMaster){
		try {
			CashMaster cashMaster = PstCashMaster.fetchExc(oidCashMaster);
			
			URL obj = new URL(posUrl+"/sendSales");
			Map<String,Object> params = new LinkedHashMap<>();
			params.put("FRM_FIELD_DATA_FOR", "cashMaster");
			params.put("command", Command.SAVE);
			params.put(FrmCashMaster.fieldNames[FrmCashMaster.FRM_FIELD_CASH_MASTER_ID], cashMaster.getOID());
			params.put(FrmCashMaster.fieldNames[FrmCashMaster.FRM_FIELD_CASHIER_NUMBER], cashMaster.getCashierNumber());
			params.put(FrmCashMaster.fieldNames[FrmCashMaster.FRM_FIELD_LOCATION_ID], cashMaster.getLocationId());
			params.put(FrmCashMaster.fieldNames[FrmCashMaster.FRM_FIELD_TAX], cashMaster.getCashTax());
			params.put(FrmCashMaster.fieldNames[FrmCashMaster.FRM_FIELD_SERVICE], cashMaster.getCashService());
			params.put(FrmCashMaster.fieldNames[FrmCashMaster.FRM_FIELD_PRICE_TYPE], cashMaster.getPriceType());
			params.put(FrmCashMaster.fieldNames[FrmCashMaster.FRM_FIELD_CASHIER_DATABASE_MODE], cashMaster.getCashierDatabaseMode());
			params.put(FrmCashMaster.fieldNames[FrmCashMaster.FRM_FIELD_CASHIER_STOCK_MODE], cashMaster.getCashierStockMode());

			StringBuilder postData = new StringBuilder();
			for (Map.Entry<String,Object> param : params.entrySet()) {
				if (postData.length() != 0) postData.append('&');
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append('=');
				postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");

			HttpURLConnection conn = (HttpURLConnection)obj.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(postDataBytes);
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		} catch (Exception exc){
			
		}
	}
	
	public void sendCashBalance(long oidCashCashier){
		try {
			Vector listCashBalance = PstBalance.list(0, 0, PstBalance.fieldNames[PstBalance.FLD_CASH_CASHIER_ID]+"="+oidCashCashier, "");
			if (listCashBalance.size()>0){
				for (int i =0; i < listCashBalance.size();i++){
					Balance balance = (Balance) listCashBalance.get(i);

					try {
						String sql = PstBalance.getInsertSQL(balance);
						runQuery(sql);
					} catch (Exception exc){
						System.out.println("sendCashBillMain:"+exc.toString());
					}
				}
			}
		} catch (Exception exc){
			
		}
	}
	
	public void sendCashBillMain(){
		try {
			BillManager.statusProses += " Process Transfer Bill Started <br>";
			String whereBillMain = "CSH.CLOSE_DATE IS NOT NULL AND CBM.BILL_STATUS = 2 ";
			Vector listBillMain = PstBillMain.listPerCashier(0, 0, whereBillMain, "CBM.CASH_CASHIER_ID");
			if (listBillMain.size() > 0){
				BillManager.statusProses += " "+listBillMain.size()+" found <br>";
				long oidCashCashier = 0;
				for (int i =0; i < listBillMain.size();i++){
					BillMain billMain = (BillMain) listBillMain.get(i);
					if (billMain.getCashCashierId() != oidCashCashier){
						try {
							CashCashier cashCashier = PstCashCashier.fetchExc(billMain.getCashCashierId());
							String sql = PstCashCashier.getInsertSQL(cashCashier);
							runQuery(sql);
							sendCashBalance(cashCashier.getOID());
							oidCashCashier = billMain.getCashCashierId();
						} catch (Exception exc){
							
						}
					}
					
					try {
						String sql = PstBillMain.getInsertSQL(billMain);
						runQuery(sql);
						BillManager.statusData = "Process : "+(i+1)+"/"+listBillMain.size()+" data<br>";
						String sqlSelect = "SELECT * FROM cash_bill_main WHERE CASH_BILL_MAIN_ID = '"+billMain.getOID()+"'";
						int result = runQueryWithResult(sqlSelect);
						if (result == 1){
							sendCashBillDetail(billMain.getOID());
							sendCreditPaymentMain(billMain.getOID());
							sendCashOtherCost(billMain.getOID());
							sendCashReturnPayment(billMain.getOID());
							sendCashRecipe(billMain.getOID());
							sendCashPayment(billMain.getOID());
							billMain.setBillStatus(5);
							PstBillMain.updateExc(billMain);
						}
					} catch (Exception exc){
						System.out.println("sendCashBillMain:"+exc.toString());
					}
				}
			} else {
				BillManager.statusProses += " No Bill found <br>";
			}
		} catch (Exception exc){
			
		}
	}
	
	public void sendCashBillDetail(long oidCashBillMain){
		try {
			Vector listCashBillDetail = PstBillDetail.list(0, 0, PstBillDetail.fieldNames[PstBillDetail.FLD_BILL_MAIN_ID]+"="+oidCashBillMain, "");
			if (listCashBillDetail.size()>0){
				for (int i =0; i < listCashBillDetail.size();i++){
					Billdetail billdetail = (Billdetail) listCashBillDetail.get(i);

					try {
						String sql = PstBillDetail.getInsertSQL(billdetail);
						runQuery(sql);
						sendPromotion(billdetail.getOID());
					} catch (Exception exc){
						System.out.println("sendCashBillMain:"+exc.toString());
					}
				}
			}
		} catch (Exception exc){
			
		}
	}
	
	public void sendPromotion(long oidBillDetail){
		try {
			Billdetail billdetail = PstBillDetail.fetchExc(oidBillDetail);
			String promotionType = PstSystemProperty.getValueByName("CASHIER_PROMOTION_GROUP_TYPE");
			DataCustom dataCustom = PstDataCustom.getDataCustom(billdetail.getOID(),""+promotionType, "bill_detail_map_type");
			String sql = PstDataCustom.getInsertSQL(dataCustom);
			runQuery(sql);
		} catch (Exception exc){
			
		}
	}
	
	public void sendCreditPaymentMain(long oidCashBillMain){
		try {
			Vector listCreditPaymentMain = PstCreditPaymentMain.list(0, 0, PstCreditPaymentMain.fieldNames[PstCreditPaymentMain.FLD_BILL_MAIN_ID]+"="+oidCashBillMain, "");
			if (listCreditPaymentMain.size()>0){
				for (int i =0; i < listCreditPaymentMain.size();i++){
					CreditPaymentMain creditPaymentMain = (CreditPaymentMain) listCreditPaymentMain.get(i);

					try {
						String sql = PstCreditPaymentMain.getInsertSQL(creditPaymentMain);
						runQuery(sql);
						sendCreditPayment(creditPaymentMain.getOID());
					} catch (Exception exc){
						System.out.println("sendCashBillMain:"+exc.toString());
					}
					
				}
			}
		} catch (Exception exc){
			
		}
	}
	
	public void sendCashOtherCost(long oidCashBillMain){
		try {
			Vector listCashOtherCost = PstOtherCost.list(0, 0, PstOtherCost.fieldNames[PstOtherCost.FLD_CASH_BILL_MAIN_ID]+"="+oidCashBillMain, "");
			if (listCashOtherCost.size()>0){
				for (int i =0; i < listCashOtherCost.size();i++){
					OtherCost otherCost = (OtherCost) listCashOtherCost.get(i);

					try {
						String sql = PstOtherCost.getInsertSQL(otherCost);
						runQuery(sql);
					} catch (Exception exc){
						System.out.println("sendCashBillMain:"+exc.toString());
					}
				}
			}
		} catch (Exception exc){
			
		}
	}
	
	public void sendCashReturnPayment(long oidCashBillMain){
		try {
			Vector listCashReturnPayment = PstCashReturn.list(0, 0, PstCashReturn.fieldNames[PstCashReturn.FLD_BILLMAIN_ID]+"="+oidCashBillMain, "");
			if (listCashReturnPayment.size()>0){
				for (int i =0; i < listCashReturnPayment.size();i++){
					CashReturn cashReturn = (CashReturn) listCashReturnPayment.get(i);

					try {
						String sql = PstCashReturn.getInsertSQL(cashReturn);
						runQuery(sql);
					} catch (Exception exc){
						System.out.println("sendCashBillMain:"+exc.toString());
					}
				}
			}
		} catch (Exception exc){
			
		}
	}
	
	public void sendCashRecipe(long oidCashBillMain){
		try {
			Vector listCashRecipe = PstRecipe.list(0, 0, PstRecipe.fieldNames[PstRecipe.FLD_CASH_BILL_MAIN_ID]+"="+oidCashBillMain, "");
			if (listCashRecipe.size()>0){
				for (int i =0; i < listCashRecipe.size();i++){
					Recipe recipe = (Recipe) listCashRecipe.get(i);

					try {
						String sql = PstRecipe.getInsertSQL(recipe);
						runQuery(sql);
					} catch (Exception exc){
						System.out.println("sendCashBillMain:"+exc.toString());
					}
				}
			}
		} catch (Exception exc){
			
		}
	}
	
	public void sendCashPayment(long oidCashBillMain){
		try {
			Vector listCashPayment = PstCashPayment.list(0, 0, PstCashPayment.fieldNames[PstCashPayment.FLD_BILL_MAIN_ID]+"="+oidCashBillMain, "");
			if (listCashPayment.size()>0){
				for (int i =0; i < listCashPayment.size();i++){
					CashPayments cashPayments = (CashPayments) listCashPayment.get(i);

					try {
						String sql = PstCashPayment.getInsertSQL(cashPayments);
						runQuery(sql);
						sendCashCreditCard(cashPayments.getOID());
					} catch (Exception exc){
						System.out.println("sendCashBillMain:"+exc.toString());
					}
				}
			}
		} catch (Exception exc){
			
		}
	}
	
	public void sendCashCreditCard(long oidCashPayment){
		try {
			Vector listCashCreditCard = PstCashCreditCard.list(0, 0, PstCashCreditCard.fieldNames[PstCashCreditCard.FLD_PAYMENT_ID]+"="+oidCashPayment, "");
			if (listCashCreditCard.size()>0){
				for (int i =0; i < listCashCreditCard.size();i++){
					CashCreditCard cashCreditCard = (CashCreditCard) listCashCreditCard.get(i);

					try {
						String sql = PstCashCreditCard.getInsertSQL(cashCreditCard);
						runQuery(sql);
					} catch (Exception exc){
						System.out.println("sendCashBillMain:"+exc.toString());
					}
				}
			}
		} catch (Exception exc){
			
		}
	}
	
	public void sendCreditPayment(long oidCashCreditPaymentMain){
		try {
			Vector listCreditPayment = PstCashCreditPayment.list(0, 0, PstCashCreditPayment.fieldNames[PstCashCreditPayment.FLD_CREDIT_MAIN_ID]+"="+oidCashCreditPaymentMain, "");
			if (listCreditPayment.size()>0){
				for (int i =0; i < listCreditPayment.size();i++){
					CashCreditPayments cashCreditPayments = (CashCreditPayments) listCreditPayment.get(i);

					try {
						String sql = PstCashCreditPayment.getInsertSQL(cashCreditPayments);
						runQuery(sql);
						sendCreditPaymentInfo(cashCreditPayments.getOID());
					} catch (Exception exc){
						System.out.println("sendCashBillMain:"+exc.toString());
					}
				}
			}
		} catch (Exception exc){
			
		}
	}
	
	public void sendCreditPaymentInfo(long oidCashCreditPayment){
		try {
			Vector listCreditPaymentInfo = PstCashCreditPaymentInfo.list(0, 0, PstCashCreditPaymentInfo.fieldNames[PstCashCreditPaymentInfo.FLD_CASH_CREDIT_PAYMENT_ID]+"="+oidCashCreditPayment, "");
			if (listCreditPaymentInfo.size()>0){
				for (int i =0; i < listCreditPaymentInfo.size();i++){
					CashCreditPaymentInfo cashCreditPaymentInfo = (CashCreditPaymentInfo) listCreditPaymentInfo.get(i);

					try {
						String sql = PstCashCreditPaymentInfo.getInsertSQL(cashCreditPaymentInfo);
						runQuery(sql);
					} catch (Exception exc){
						System.out.println("sendCashBillMain:"+exc.toString());
					}
				}
			}
		} catch (Exception exc){
			
		}
	}
        
	public void sendDispatch(){
		try {
			String whereDispatch = PstMatDispatch.fieldNames[PstMatDispatch.FLD_DISPATCH_STATUS]+" = 5";
			Vector listDispatch = PstMatDispatch.list(0, 0, whereDispatch, "");
			if (listDispatch.size()>0){
				for (int i =0; i < listDispatch.size();i++){
					MatDispatch matDispatch = (MatDispatch) listDispatch.get(i);

					Calendar disDate = Calendar.getInstance();
					if (matDispatch.getDispatchDate()!= null){
						disDate.setTime(matDispatch.getDispatchDate());
					}
					
					URL obj = new URL(posUrl+"/sendSales");
					Map<String,Object> params = new LinkedHashMap<>();
					params.put("FRM_FIELD_DATA_FOR", "dispatch");
					params.put("command", Command.SAVE);
					params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_DISPATCH_MATERIAL_ID], matDispatch.getOID());
					params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_LOCATION_ID], matDispatch.getLocationId());
					params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_LOCATION_TYPE], matDispatch.getLocationType());
					if (matDispatch.getDispatchDate() != null){
						params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_DISPATCH_DATE]+"_yr", disDate.get(Calendar.YEAR));
						params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_DISPATCH_DATE]+"_mn", disDate.get(Calendar.MONTH)+1);
						params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_DISPATCH_DATE]+"_dy", disDate.get(Calendar.DAY_OF_MONTH));
						params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_DISPATCH_DATE]+"_mm", disDate.get(Calendar.MINUTE));
						params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_DISPATCH_DATE]+"_hh", disDate.get(Calendar.HOUR_OF_DAY));
					}
					params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_DISPATCH_STATUS], matDispatch.getDispatchStatus());
					params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_REMARK], matDispatch.getRemark());
					params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_DISPATCH_CODE], matDispatch.getDispatchCode());
					params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_DISPATCH_CODE_COUNTER], matDispatch.getDispatchCodeCounter());
					params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_DISPATCH_TO], matDispatch.getDispatchTo());
					params.put(FrmMatDispatch.fieldNames[FrmMatDispatch.FRM_FIELD_INVOICE_SUPPLIER], matDispatch.getInvoiceSupplier());
					
					StringBuilder postData = new StringBuilder();
					for (Map.Entry<String,Object> param : params.entrySet()) {
						if (postData.length() != 0) postData.append('&');
						postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
						postData.append('=');
						postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
					}
					byte[] postDataBytes = postData.toString().getBytes("UTF-8");

					HttpURLConnection conn = (HttpURLConnection)obj.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
					conn.setDoOutput(true);
					conn.getOutputStream().write(postDataBytes);
					Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					sendDispatchItem(matDispatch.getOID());
					try {
						matDispatch.setDispatchStatus(7);
						PstMatDispatch.updateExc(matDispatch);
					} catch (Exception exc){}
				}
			}
		} catch (Exception exc){
			
		}
	}
	
	public void sendDispatchItem(long dispatchId){
		try {
			String whereDispatchItem = PstMatDispatchItem.fieldNames[PstMatDispatchItem.FLD_DISPATCH_MATERIAL_ID]+" = "+dispatchId;
			Vector listDispatchItem = PstMatDispatchItem.list(0, 0, whereDispatchItem, "");
			if (listDispatchItem.size()>0){
				for (int i =0; i < listDispatchItem.size();i++){
					MatDispatchItem matDispatchItem = (MatDispatchItem) listDispatchItem.get(i);
					
					URL obj = new URL(posUrl+"/sendSales");
					Map<String,Object> params = new LinkedHashMap<>();
					params.put("FRM_FIELD_DATA_FOR", "dispatchItem");
					params.put("command", Command.SAVE);
					params.put(FrmMatDispatchItem.fieldNames[FrmMatDispatchItem.FRM_FIELD_DISPATCH_MATERIAL_ITEM_ID], matDispatchItem.getOID());
					params.put(FrmMatDispatchItem.fieldNames[FrmMatDispatchItem.FRM_FIELD_DISPATCH_MATERIAL_ID], matDispatchItem.getDispatchMaterialId());
					params.put(FrmMatDispatchItem.fieldNames[FrmMatDispatchItem.FRM_FIELD_MATERIAL_ID], matDispatchItem.getMaterialId());
					params.put(FrmMatDispatchItem.fieldNames[FrmMatDispatchItem.FRM_FIELD_UNIT_ID], matDispatchItem.getUnitId());
					params.put(FrmMatDispatchItem.fieldNames[FrmMatDispatchItem.FRM_FIELD_QTY], matDispatchItem.getQty());
					params.put(FrmMatDispatchItem.fieldNames[FrmMatDispatchItem.FRM_FIELD_HPP], matDispatchItem.getHpp());
					params.put(FrmMatDispatchItem.fieldNames[FrmMatDispatchItem.FRM_FIELD_HPP_TOTAL], matDispatchItem.getHppTotal());
					
					StringBuilder postData = new StringBuilder();
					for (Map.Entry<String,Object> param : params.entrySet()) {
						if (postData.length() != 0) postData.append('&');
						postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
						postData.append('=');
						postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
					}
					byte[] postDataBytes = postData.toString().getBytes("UTF-8");

					HttpURLConnection conn = (HttpURLConnection)obj.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
					conn.setDoOutput(true);
					conn.getOutputStream().write(postDataBytes);
					Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				}
			}
		} catch (Exception exc){
			
		}
	}
	
	public void sendReceive(){
		try {
			String whereReceive = PstMatReceive.fieldNames[PstMatReceive.FLD_RECEIVE_STATUS]+" = 5";
			Vector listReceive = PstMatReceive.list(0, 0, whereReceive, "");
			if (listReceive.size()>0){
				for (int i =0; i < listReceive.size();i++){
					MatReceive matReceive = (MatReceive) listReceive.get(i);

					Calendar recDate = Calendar.getInstance();
					if (matReceive.getReceiveDate()!= null){
						recDate.setTime(matReceive.getReceiveDate());
					}
					
					Calendar expDate = Calendar.getInstance();
					if (matReceive.getExpiredDate()!= null){
						expDate.setTime(matReceive.getExpiredDate());
					}
					
					Calendar updateDate = Calendar.getInstance();
					if (matReceive.getLastUpdate()!= null){
						updateDate.setTime(matReceive.getLastUpdate());
					}
					
					URL obj = new URL(posUrl+"/sendSales");
					Map<String,Object> params = new LinkedHashMap<>();
					params.put("FRM_FIELD_DATA_FOR", "receive");
					params.put("command", Command.SAVE);
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_MATERIAL_ID], matReceive.getOID());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_LOCATION_ID], matReceive.getLocationId());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_LOCATION_TYPE], matReceive.getLocationType());
					if (matReceive.getReceiveDate() != null){
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_DATE]+"_yr", recDate.get(Calendar.YEAR));
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_DATE]+"_mn", recDate.get(Calendar.MONTH)+1);
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_DATE]+"_dy", recDate.get(Calendar.DAY_OF_MONTH));
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_DATE]+"_mm", recDate.get(Calendar.MINUTE));
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_DATE]+"_hh", recDate.get(Calendar.HOUR_OF_DAY));
					}
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_STATUS], matReceive.getReceiveStatus());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_SOURCE], matReceive.getReceiveSource());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_SUPPLIER_ID], matReceive.getSupplierId());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_PURCHASE_ORDER_ID], matReceive.getPurchaseOrderId());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_REMARK], matReceive.getRemark());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_FROM], matReceive.getReceiveFrom());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_REC_CODE], matReceive.getRecCode());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_REC_CODE_CNT], matReceive.getRecCodeCnt());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_DISPATCH_MATERIAL_ID], matReceive.getDispatchMaterialId());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RETURN_MATERIAL_ID], matReceive.getReturnMaterialId());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_INVOICE_SUPPLIER], matReceive.getInvoiceSupplier());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_TOTAL_PPN], matReceive.getTotalPpn());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_REASON], matReceive.getReason());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_DISCOUNT], matReceive.getDiscount());
					if (matReceive.getExpiredDate()!= null){
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_EXPIRED_DATE]+"_yr", expDate.get(Calendar.YEAR));
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_EXPIRED_DATE]+"_mn", expDate.get(Calendar.MONTH)+1);
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_EXPIRED_DATE]+"_dy", expDate.get(Calendar.DAY_OF_MONTH));	
					}
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_CREDIT_TIME], matReceive.getCreditTime());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_CURRENCY_ID], matReceive.getCurrencyId());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_CREDIT_TIME], matReceive.getCreditTime());
					if (matReceive.getLastUpdate()!= null){
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_DATE]+"_yr", updateDate.get(Calendar.YEAR));
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_DATE]+"_mn", updateDate.get(Calendar.MONTH)+1);
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_DATE]+"_dy", updateDate.get(Calendar.DAY_OF_MONTH));
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_DATE]+"_mm", updateDate.get(Calendar.MINUTE));
						params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_RECEIVE_DATE]+"_hh", updateDate.get(Calendar.HOUR_OF_DAY));
					}
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_TERM_OF_PAYMENT], matReceive.getTermOfPayment());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_TRANS_RATE], matReceive.getTransRate());
					params.put(FrmMatReceive.fieldNames[FrmMatReceive.FRM_FIELD_INCLUDE_PPN], matReceive.getIncludePpn());
					
					StringBuilder postData = new StringBuilder();
					for (Map.Entry<String,Object> param : params.entrySet()) {
						if (postData.length() != 0) postData.append('&');
						postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
						postData.append('=');
						postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
					}
					byte[] postDataBytes = postData.toString().getBytes("UTF-8");

					HttpURLConnection conn = (HttpURLConnection)obj.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
					conn.setDoOutput(true);
					conn.getOutputStream().write(postDataBytes);
					Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
					sendReceiveItem(matReceive.getOID());
					try {
						matReceive.setReceiveStatus(7);
						PstMatReceive.updateExc(matReceive);
					} catch (Exception exc){}
				}
			}
		} catch (Exception exc){
			
		}
	}
   
    public void sendReceiveItem(long receiveId){
		try {
			String whereReceiveItem = PstMatReceiveItem.fieldNames[PstMatReceiveItem.FLD_RECEIVE_MATERIAL_ID]+" = "+receiveId;
			Vector listReceiveItem = PstMatReceiveItem.list(0, 0, whereReceiveItem, "");
			if (listReceiveItem.size()>0){
				for (int i =0; i < listReceiveItem.size();i++){
					MatReceiveItem matReceiveItem = (MatReceiveItem) listReceiveItem.get(i);
					
					Calendar expDate = Calendar.getInstance();
					if (matReceiveItem.getExpiredDate()!= null){
						expDate.setTime(matReceiveItem.getExpiredDate());
					}
					
					URL obj = new URL(posUrl+"/sendSales");
					Map<String,Object> params = new LinkedHashMap<>();
					params.put("FRM_FIELD_DATA_FOR", "receiveItem");
					params.put("command", Command.SAVE);
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_RECEIVE_MATERIAL_ITEM_ID], matReceiveItem.getOID());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_RECEIVE_MATERIAL_ID], matReceiveItem.getReceiveMaterialId());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_MATERIAL_ID], matReceiveItem.getMaterialId());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_UNIT_ID], matReceiveItem.getUnitId());
					if (matReceiveItem.getExpiredDate()!= null){
						params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_EXPIRED_DATE]+"_yr", expDate.get(Calendar.YEAR));
						params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_EXPIRED_DATE]+"_mn", expDate.get(Calendar.MONTH)+1);
						params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_EXPIRED_DATE]+"_dy", expDate.get(Calendar.DAY_OF_MONTH));	
					}
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_COST], matReceiveItem.getCost());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_CURRENCY_ID], matReceiveItem.getCurrencyId());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_QTY], matReceiveItem.getQty());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_DISCOUNT], matReceiveItem.getDiscount());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_TOTAL], matReceiveItem.getTotal());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_DISC_NOMINAL], matReceiveItem.getDiscNominal());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_DISCOUNT2], matReceiveItem.getDiscount2());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_CURR_BUYING_PRICE], matReceiveItem.getCurrBuyingPrice());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_FORWARDER_COST], matReceiveItem.getForwarderCost());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_QTY_INPUT], matReceiveItem.getQtyEntry());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_UNIT_ID_KONVERSI], matReceiveItem.getUnitKonversi());
					params.put(FrmMatReceiveItem.fieldNames[FrmMatReceiveItem.FRM_FIELD_PRICE_KONVERSI], matReceiveItem.getPriceKonv());
					
					StringBuilder postData = new StringBuilder();
					for (Map.Entry<String,Object> param : params.entrySet()) {
						if (postData.length() != 0) postData.append('&');
						postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
						postData.append('=');
						postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
					}
					byte[] postDataBytes = postData.toString().getBytes("UTF-8");

					HttpURLConnection conn = (HttpURLConnection)obj.openConnection();
					conn.setRequestMethod("POST");
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
					conn.setDoOutput(true);
					conn.getOutputStream().write(postDataBytes);
					Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				}
			}
		} catch (Exception exc){
			
		}
	}
	
	public void getCatalog(){
		CashCashier cashCashier = new CashCashier();
		CatalogManager catalogManager = new CatalogManager();
        Location location = new Location();
        Vector listOpeningCashier =  PstCashCashier.listCashOpening(0, 0, 
                "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID]+"='"+userId+"' "
                + "AND CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID]+"='1'", "");   
        Vector openingCashier = new Vector(1,1);
        Vector listBillMain = new Vector(1,1);
        if(listOpeningCashier.size() != 0){
            openingCashier = (Vector) listOpeningCashier.get(0);
            if(openingCashier.size() != 0 ){
                cashCashier = (CashCashier) openingCashier.get(0);
                location = (Location) openingCashier.get(2);
            }
        }
		
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        Connection conn = null;
        Statement statement = null;
		CatalogManager.statusProses += " Get Catalog Process Started<br>";
		//get count dulu lah ada data apa gak
        String sqlCount = "SELECT COUNT("+PstDataCustom.fieldNames[PstDataCustom.FLD_DATA_CUSTOM_ID]+") FROM "+PstDataCustom.TBL_DATA_CUSTOM + " WHERE " + 
					PstDataCustom.fieldNames[PstDataCustom.FLD_OWNER_ID]+"="+location.getOID()
					+ " AND "+ PstDataCustom.fieldNames[PstDataCustom.FLD_DATA_NAME]+"= 'sync_data'"
					+ " ORDER BY data_count";
		try {
			
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sqlCount);
			int data = 0;
			while (rs.next()) {
				data = rs.getInt(1);
				CatalogManager.statusProses += " Connection Status : OK<br>";
				CatalogManager.statusProses += data+" data to be transfered<br>";
			}
			if (data > 0){
				
				int x = 0;
				do {
					String sql = "SELECT * FROM "+PstDataCustom.TBL_DATA_CUSTOM + " WHERE " + 
					PstDataCustom.fieldNames[PstDataCustom.FLD_OWNER_ID]+"="+location.getOID()
					+ " AND "+ PstDataCustom.fieldNames[PstDataCustom.FLD_DATA_NAME]+"= 'sync_data'"
					+ " ORDER BY data_count LIMIT 250";
					int eror = 0;
					int number = 0;
					int sizeData = 0;
					try {
						conn = DriverManager.getConnection(dbURL, username, password);
						statement = conn.createStatement();
						rs = statement.executeQuery(sql);
						
						if (rs.last()){
							sizeData = rs.getRow();
							rs.beforeFirst();
						}
						
						String[] sqlQuery = new String[sizeData];
						String[] strOid = new String[sizeData];
						
						while (rs.next()) {
							DataCustom dataCustom = new DataCustom();
							PstDataCustom.resultToObject(rs, dataCustom);
							
							sqlQuery[number] = dataCustom.getDataValue();
							strOid[number] = String.valueOf(dataCustom.getOID());
							number++;
						}
						
						try {
							int[] result = DBHandler.execUpdateBatchServices(sqlQuery);
							int i = 0;
							String inOid = "";
							for (int s : result){
								if (s < 0){
									LogSysHistory logSys = new LogSysHistory();
									logSys.setLogApplication("Cashier");
									logSys.setLogDetail(sqlQuery[i]);
									logSys.setLogDocumentId(0);
									logSys.setLogDocumentNumber("-");
									logSys.setLogLoginName("");
									logSys.setLogUpdateDate(new Date());
									logSys.setLogUserAction("Error Insert");
									try {
										PstLogSysHistory.insertExc(logSys);
									} catch (Exception ex){}
								}
								inOid+=strOid[i]+",";
								i++;
							}
							if (inOid.length()>0){
								inOid = inOid.substring(0, inOid.length() - 1);
							}
							deleteDataCustomIn(inOid);
						} catch (Exception exc){

						}
						
						//CatalogManager.statusProses += printNoData;
					} catch(SQLException se){
						se.printStackTrace();
						CatalogManager.statusProses += " Connection Status : Error Can't Connect to Server<br>";
					} catch(Exception e){
					   //Handle errors for Class.forName
					   e.printStackTrace();
					   CatalogManager.statusProses += " Connection Status : Error Can't Connect to Server<br>";
					} finally{
						try {
							if(rs!=null) rs.close(); //close resultSet
							if(statement!=null) statement.close(); //close statement
							if(conn!=null) conn.close(); // close connection
						} catch (SQLException e) {
							e.printStackTrace();
						}
				   }
					x += sizeData;
					CatalogManager.statusData = "Process : "+x+"/"+data+" data<br>";
				} while (x < data);
				CatalogManager.statusData += " Transfer completed <br>";
				CatalogManager.statusData += " Service will idle for 2 minute<br>";
			} else {
				CatalogManager.statusProses += "No data to be transfered.<br>";
			}
			
		} catch(SQLException se){
			se.printStackTrace();
			CatalogManager.statusProses += " Connection Status : Error Can't Connect to Server<br>";
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		   CatalogManager.statusProses += " Connection Status : Error Can't Connect to Server<br>";
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			   CatalogManager.statusProses += " Connection Status : Error Can't Connect to Server<br>";
			}//end finally try
		 }
	}
	
	public void getCatalogAPI(){
		CashCashier cashCashier = new CashCashier();
		CatalogManager catalogManager = new CatalogManager();
        Location location = new Location();
        Vector listOpeningCashier =  PstCashCashier.listCashOpening(0, 0, 
                "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID]+"='"+userId+"' "
                + "AND CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID]+"='1'", "");   
        Vector openingCashier = new Vector(1,1);
        Vector listBillMain = new Vector(1,1);
        if(listOpeningCashier.size() != 0){
            openingCashier = (Vector) listOpeningCashier.get(0);
            if(openingCashier.size() != 0 ){
                cashCashier = (CashCashier) openingCashier.get(0);
                location = (Location) openingCashier.get(2);
            }
        }
		
		CatalogManager.statusProses = " Get Catalog Process Started<br>";
		
		try {
			JSONObject json = readJsonFromUrl(posUrl+"/syncCatalog?LOCATION_ID="+location.getOID()+"&FRM_FIELD_DATA_FOR='getData'");
			int dataCount = json.getInt("COUNT");
			if (dataCount>0){
				CatalogManager.statusProses += " Connection Status : OK<br>";
				CatalogManager.statusProses += dataCount+" data to be transfered<br>";
				
				String[] sqlQuery = new String[dataCount];
				String[] strOid = new String[dataCount];
				JSONArray arr = json.getJSONArray("DATA");
				for (int i = 0; i < arr.length(); i++) {
					long dataCustomId = arr.getJSONObject(i).getLong(PstDataCustom.fieldNames[PstDataCustom.FLD_DATA_CUSTOM_ID]);
					String query = arr.getJSONObject(i).getString(PstDataCustom.fieldNames[PstDataCustom.FLD_DATA_VALUE]);
					sqlQuery[i] = query;
					strOid[i] = ""+dataCustomId;
					CatalogManager.statusData = "Process : "+(i+1)+"/"+dataCount+" data<br>";
				}
				if (sqlQuery.length>0){
					try {
						CatalogManager.statusProses += " Execute Data, This may take a while... <br>";
						int[] result = DBHandler.execUpdateBatchServices(sqlQuery);
						int i = 0;
						String inOid = "";
						for (int s : result){
							if (s < 0){
								LogSysHistory logSys = new LogSysHistory();
								logSys.setLogApplication("Cashier");
								logSys.setLogDetail(sqlQuery[i]);
								logSys.setLogDocumentId(0);
								logSys.setLogDocumentNumber("-");
								logSys.setLogLoginName("");
								logSys.setLogUpdateDate(new Date());
								logSys.setLogUserAction("Error Insert");
								try {
									PstLogSysHistory.insertExc(logSys);
								} catch (Exception ex){}
							}
							inOid+=strOid[i]+",";
							i++;
						}
						URL obj = new URL(posUrl+"/syncCatalog");
						Map<String,String> params = new LinkedHashMap<>();
						params.put("deleteData","FRM_FIELD_DATA_FOR");
						for (String oid : strOid){
							params.put(oid, PstDataCustom.fieldNames[PstDataCustom.FLD_DATA_CUSTOM_ID]);
						}
						StringBuilder postData = new StringBuilder();
						for (Map.Entry<String,String> param : params.entrySet()) {
							if (postData.length() != 0) postData.append('&');
							postData.append(URLEncoder.encode(param.getValue(), "UTF-8"));
							postData.append('=');
							postData.append(URLEncoder.encode(String.valueOf(param.getKey()), "UTF-8"));
						}
						byte[] postDataBytes = postData.toString().getBytes("UTF-8");

						HttpURLConnection conn = (HttpURLConnection)obj.openConnection();
						conn.setRequestMethod("POST");
						conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
						conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
						conn.setDoOutput(true);
						conn.getOutputStream().write(postDataBytes);
						Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
						CatalogManager.statusProses += " Transfer completed <br>";
						CatalogManager.statusProses += " Service will idle for 2 minute<br>";
					} catch (Exception exc){

					}
				}
				
			} else {
				CatalogManager.statusProses += "No data to be transfered.<br>";
			}
			
			
		} catch(Exception e){
			CatalogManager.statusProses += " Connection Status : Error Can't Connect to Server<br>";
		} 
	}
	
	public static void deleteDataCustom(long oidDataCustom){
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        Connection conn = null;
        Statement statement = null;
        
		String sql = "DELETE FROM "+PstDataCustom.TBL_DATA_CUSTOM + " WHERE " + 
					PstDataCustom.fieldNames[PstDataCustom.FLD_DATA_CUSTOM_ID]+"="+oidDataCustom;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            int result = statement.executeUpdate(sql);
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
	}
	
	public void deleteDataCustomIn(String inOid){
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        Connection conn = null;
        Statement statement = null;
        
		String sql = "DELETE FROM "+PstDataCustom.TBL_DATA_CUSTOM + " WHERE " + 
					PstDataCustom.fieldNames[PstDataCustom.FLD_DATA_CUSTOM_ID]+" IN ("+inOid+")";
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            int result = statement.executeUpdate(sql);
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
	}
	
	public void sendDispatchV2(){
		
		String whereClause = PstMatDispatch.fieldNames[PstMatDispatch.FLD_DISPATCH_STATUS] + " = 5";
		Vector listDispatch = PstMatDispatch.list(0, 0, whereClause, "");
		if (listDispatch.size() > 0){
			for (int i = 0; i < listDispatch.size(); i++){
				MatDispatch matDispatch = (MatDispatch) listDispatch.get(i);
				try {
					String sql = PstMatDispatch.getInsertSQL(matDispatch);
					runQuery(sql);
					String sqlSelect = "SELECT * FROM pos_dispatch_material WHERE DISPATCH_MATERIAL_ID = '"+matDispatch.getOID()+"'";
					int result = runQueryWithResult(sqlSelect);
					if (result == 1){
						sendDispatchItemV2(matDispatch.getOID());
						sendReceiveV2(matDispatch.getOID());
						matDispatch.setDispatchStatus(7);
						PstMatDispatch.updateExc(matDispatch);
					}
				} catch (Exception exc){
					System.out.println("SendDispatchV2:"+exc.toString());
				}
			}
		}
	}
	
	public void sendDispatchItemV2(long oidDispatch){
		
		String whereClause = PstMatDispatchItem.fieldNames[PstMatDispatchItem.FLD_DISPATCH_MATERIAL_ID] + " = "+oidDispatch;
		Vector listDispatchItem = PstMatDispatchItem.list(0, 0, whereClause, "");
		if (listDispatchItem.size() > 0){
			for (int i = 0; i < listDispatchItem.size(); i++){
				MatDispatchItem matDispatchItem = (MatDispatchItem) listDispatchItem.get(i);
				try {
					String sql = PstMatDispatchItem.getInsertSQL(matDispatchItem);
					runQuery(sql);
				} catch (Exception exc){
					System.out.println("sendDispatchItemV2:"+exc.toString());
				}
			}
		}
	}
	
	public List<String> sendDispatchItemV2(List<String> query, long oidDispatch){
		
		String whereClause = PstMatDispatchItem.fieldNames[PstMatDispatchItem.FLD_DISPATCH_MATERIAL_ID] + " = "+oidDispatch;
		Vector listDispatchItem = PstMatDispatchItem.list(0, 0, whereClause, "");
		if (listDispatchItem.size() > 0){
			for (int i = 0; i < listDispatchItem.size(); i++){
				MatDispatchItem matDispatchItem = (MatDispatchItem) listDispatchItem.get(i);
				try {
					query.add(PstMatDispatchItem.getInsertSQL(matDispatchItem));
				} catch (Exception exc){
					System.out.println("sendDispatchItemV2:"+exc.toString());
				}
			}
		}
		return query;
	}
	
	public void sendReceiveV2(long oidMatDispatch){
		
		String whereClause = PstMatReceive.fieldNames[PstMatReceive.FLD_RECEIVE_STATUS] + " = 5 AND "
				+ PstMatReceive.fieldNames[PstMatReceive.FLD_RECEIVE_SOURCE] + " = 2 AND "
				+ PstMatReceive.fieldNames[PstMatReceive.FLD_DISPATCH_MATERIAL_ID] + " = " + oidMatDispatch;
		Vector listReceive = PstMatReceive.list(0, 0, whereClause, "");
		if (listReceive.size() > 0){
			for (int i = 0; i < listReceive.size(); i++){
				MatReceive matReceive = (MatReceive) listReceive.get(i);
				try {
					String sql = PstMatReceive.getInsertSQL(matReceive);
					runQuery(sql);
					sendReceiveItemV2(matReceive.getOID());
					matReceive.setReceiveStatus(7);
					PstMatReceive.updateExc(matReceive);
				} catch (Exception exc){
					System.out.println("SendDispatchV2:"+exc.toString());
				}
			}
		}
	}
	
	public List<String> sendReceiveV2(List<String> query, long oidMatDispatch){
		
		String whereClause = PstMatReceive.fieldNames[PstMatReceive.FLD_RECEIVE_STATUS] + " = 5 AND "
				+ PstMatReceive.fieldNames[PstMatReceive.FLD_RECEIVE_SOURCE] + " = 2 AND "
				+ PstMatReceive.fieldNames[PstMatReceive.FLD_DISPATCH_MATERIAL_ID] + " = " + oidMatDispatch;
		Vector listReceive = PstMatReceive.list(0, 0, whereClause, "");
		if (listReceive.size() > 0){
			for (int i = 0; i < listReceive.size(); i++){
				MatReceive matReceive = (MatReceive) listReceive.get(i);
				try {
					query.add(PstMatReceive.getInsertSQL(matReceive));
					query = sendReceiveItemV2(query, matReceive.getOID());
				} catch (Exception exc){
					System.out.println("SendDispatchV2:"+exc.toString());
				}
			}
		}
		return query;
	}
	
	public void sendReceiveItemV2(long oidRec){
		
		String whereClause = PstMatReceiveItem.fieldNames[PstMatReceiveItem.FLD_RECEIVE_MATERIAL_ID] + " = "+oidRec;
		Vector listReceiveItem = PstMatReceiveItem.list(0, 0, whereClause, "");
		if (listReceiveItem.size() > 0){
			for (int i = 0; i < listReceiveItem.size(); i++){
				MatReceiveItem matReceiveItem = (MatReceiveItem) listReceiveItem.get(i);
				try {
					String sql = PstMatReceiveItem.getInsertSQL(matReceiveItem);
					runQuery(sql);
				} catch (Exception exc){
					System.out.println("sendDispatchItemV2:"+exc.toString());
				}
			}
		}
	}
	
	public List<String> sendReceiveItemV2(List<String> query, long oidRec){
		
		String whereClause = PstMatReceiveItem.fieldNames[PstMatReceiveItem.FLD_RECEIVE_MATERIAL_ID] + " = "+oidRec;
		Vector listReceiveItem = PstMatReceiveItem.list(0, 0, whereClause, "");
		if (listReceiveItem.size() > 0){
			for (int i = 0; i < listReceiveItem.size(); i++){
				MatReceiveItem matReceiveItem = (MatReceiveItem) listReceiveItem.get(i);
				try {
					query.add(PstMatReceiveItem.getInsertSQL(matReceiveItem));
				} catch (Exception exc){
					System.out.println("sendDispatchItemV2:"+exc.toString());
				}
			}
		}
		return query;
	}
	
	public void runQuery(String sql){
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        Connection conn = null;
        Statement statement = null;
        
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            int result = statement.executeUpdate(sql);
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
	}
	
	public int runQueryWithResult(String sql){
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        Connection conn = null;
        Statement statement = null;
		
		int result = 0;
        
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				result = 1;
				
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
		
		return result;
	}
	
	public void syncMaster(){
		getPosUnit();
		getMasterType();
		getPosMerk();
		getPosSubCategory();
		getPosCategory();
		getPosColor();
		getContactList();
		getPosMaterial();
		getPosMaterialTypeMapping();
		getProductKsg();
		getPosPriceTypeMapping();
		getProductLocation();
		getPosSales();
	}
    
    public void getPosUnit(){
        
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstUnit.TBL_P2_UNIT;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Unit matUnit = new Unit();
                resultToObjectUnit(rs, matUnit);
				boolean check = PstUnit.checkOID(matUnit.getOID());
				try {
					if (check){
						PstUnit.updateExc(matUnit);
						System.out.println("update unit data success :" + matUnit.getOID());
					} else {
						PstUnit.insertExc(matUnit);
						System.out.println("insert unit data success :" + matUnit.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on getUnit :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	public void getMasterType(){
        
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
		
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstMasterType.TBL_MASTER_TYPE;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				MasterType mastertype = new MasterType();
				PstMasterType.resultToObject(rs, mastertype);
				boolean check = PstMasterType.checkOID(mastertype.getOID());
				try {
					if (check){
						PstMasterType.updateExc(mastertype);
						System.out.println("update master type data success :" + mastertype.getOID());
					} else {
						PstMasterType.insertExcByOid(mastertype);
						System.out.println("insert master type data success :" + mastertype.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on mastertype :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	public void getPosMerk(){
		
        try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
		
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstMerk.TBL_MAT_MERK;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Merk matmerk = new Merk();
                PstMerk.resultToObject(rs, matmerk);
				boolean check = PstMerk.checkOID(matmerk.getOID());
				try {
					if (check){
						PstMerk.updateExc(matmerk);
						System.out.println("update merk data success :" + matmerk.getOID());
					} else {
						PstMerk.insertExcByOid(matmerk);
						System.out.println("insert merk data success :" + matmerk.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on matmerk :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	public void getPosSubCategory(){
        
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
		
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstSubCategory.TBL_SUB_CATEGORY;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				SubCategory subCategory = new SubCategory();
				PstSubCategory.resultToObject(rs, subCategory);
				boolean check = PstSubCategory.checkOID(subCategory.getOID());
				try {
					if (check){
						PstSubCategory.updateExc(subCategory);
						System.out.println("update sub category data success :" + subCategory.getOID());
					} else {
						PstSubCategory.insertExcByOid(subCategory);
						System.out.println("insert sub category data success :" + subCategory.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on subCategory :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	public void getPosCategory(){
		
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstCategory.TBL_CATEGORY;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Category category = new Category();
                PstCategory.resultToObject(rs, category);
				boolean check = PstCategory.checkOID(category.getOID());
				try {
					if (check){
						PstCategory.updateExc(category);
						System.out.println("update category data success :" + category.getOID());
					} else {
						PstCategory.insertExcOid(category);
						System.out.println("insert category data success :" + category.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on category :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	public void getPosColor(){
		
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstColor.TBL_POS_COLOR;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Color entColor = new Color();
                PstColor.resultToObject(rs, entColor);
				boolean check = PstColor.checkOID(entColor.getOID());
				try {
					if (check){
						PstColor.updateExc(entColor);
						System.out.println("update color data success :" + entColor.getOID());
					} else {
						PstColor.insertExcByOid(entColor);
						System.out.println("insert color data success :" + entColor.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on entColor :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	public void getContactList(){
		
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstContactList.TBL_CONTACT_LIST;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				ContactList contactlist = new ContactList();
                PstContactList.resultToObject(rs, contactlist);
				boolean check = PstContactList.checkOID(contactlist.getOID());
				try {
					if (check){
						PstContactList.updateExc(contactlist);
						System.out.println("update contact data success :" + contactlist.getOID());
					} else {
						PstContactList.insertExcByOid(contactlist);
						System.out.println("insert contact data success :" + contactlist.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on contactlist :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	public void getPosMaterialTypeMapping(){
		
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstMaterialMappingType.TBL_MATERIAL_TYPE_MAPPING;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				MaterialTypeMapping entMaterialTypeMapping = new MaterialTypeMapping();
                PstMaterialMappingType.resultToObject(rs, entMaterialTypeMapping);
				boolean check = PstMaterialMappingType.checkOID(entMaterialTypeMapping.getOID());
				try {
					if (check){
						PstMaterialMappingType.updateExc(entMaterialTypeMapping);
						System.out.println("update material mapping type data success :" + entMaterialTypeMapping.getOID());
					} else {
						PstMaterialMappingType.insertExcByOid(entMaterialTypeMapping);
						System.out.println("insert material mapping type data success :" + entMaterialTypeMapping.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on entMaterialTypeMapping :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	public void getProductKsg(){
		
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstMatMappKsg.TBL_POS_MAT_KSG;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				MatMappKsg matMappKsg = new MatMappKsg();
                PstMatMappKsg.resultToObject(rs, matMappKsg);
				boolean check = PstMatMappKsg.checkOID(matMappKsg.getKsgId(), matMappKsg.getMaterialId());
				try {
					if (check){
						PstMatMappKsg.updateExc(matMappKsg);
						System.out.println("update mat map ksg data success :" + matMappKsg.getOID());
					} else {
						PstMatMappKsg.insertExcByOid(matMappKsg);
						System.out.println("insert mat map ksg data success :" + matMappKsg.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on matMappKsg :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	public void getPosMaterial(){
		
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstMaterial.TBL_MATERIAL;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Material material = new Material();
                PstMaterial.resultToObject(rs, material);
				boolean check = PstMaterial.checkOID(material.getOID());
				try {
					if (check){
						PstMaterial.updateExc(material);
						System.out.println("update material data success :" + material.getOID());
					} else {
						PstMaterial.insertExcByOid(material);
						System.out.println("insert material data success :" + material.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on material :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	public void getPosPriceTypeMapping(){
        
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
		
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstPriceTypeMapping.TBL_POS_PRICE_TYPE_MAPPING;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				PriceTypeMapping pricetypemapping = new PriceTypeMapping();
                PstPriceTypeMapping.resultToObject(rs, pricetypemapping);
				boolean check = PstPriceTypeMapping.checkOID(pricetypemapping.getPriceTypeId(), pricetypemapping.getMaterialId(), pricetypemapping.getStandartRateId());
				try {
					if (check){
						PstPriceTypeMapping.updateExc(pricetypemapping);
						System.out.println("update price mapping data success :" + pricetypemapping.getOID());
					} else {
						PstPriceTypeMapping.insertExcByOid(pricetypemapping);
						System.out.println("insert price mapping data success :" + pricetypemapping.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on pricetypemapping :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	public void getProductLocation(){
		
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstMatMappLocation.TBL_POS_MAT_LOCATION;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				MatMappLocation matMappLocation = new MatMappLocation();
                PstMatMappLocation.resultToObject(rs, matMappLocation);
				boolean check = PstMatMappLocation.checkOID(matMappLocation.getLocationId(), matMappLocation.getMaterialId());
				try {
					if (check){
						PstMatMappLocation.updateExc(matMappLocation);
						System.out.println("update product location data success :" + matMappLocation.getOID());
					} else {
						PstMatMappLocation.insertExcByOid(matMappLocation);
						System.out.println("insert product location data success :" + matMappLocation.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on matMappLocation :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	public void getPosSales(){
		
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        
        Connection conn = null;
        Statement statement = null;
        
		String sql = "SELECT * FROM "+PstSales.TBL_SALES;
		
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				Sales matSales = new Sales();
                PstSales.resultToObject(rs, matSales);
				boolean check = PstSales.checkOID(matSales.getOID());
				try {
					if (check){
						PstSales.updateExc(matSales);
						System.out.println("update sales data success :" + matSales.getOID());
					} else {
						PstSales.insertExcByOid(matSales);
						System.out.println("update sales data success :" + matSales.getOID());
					}
				} catch (Exception exc){
					System.out.println("Exception on matSales :" + exc.toString());
				}
			}
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
    }
	
	private static void resultToObjectUnit(ResultSet rs, Unit matUnit) {
        try {
            matUnit.setOID(rs.getLong(PstUnit.fieldNames[PstUnit.FLD_UNIT_ID]));
            matUnit.setCode(rs.getString(PstUnit.fieldNames[PstUnit.FLD_CODE]));
            matUnit.setName(rs.getString(PstUnit.fieldNames[PstUnit.FLD_NAME]));
            matUnit.setQtyPerBaseUnit(rs.getDouble(PstUnit.fieldNames[PstUnit.FLD_QTY_PER_BASE_UNIT]));
            matUnit.setBaseUnitId(rs.getLong(PstUnit.fieldNames[PstUnit.FLD_BASE_UNIT_ID]));
        } catch (Exception e) {
        }
    }
	
	/*public void getCatalogJSON(){
		CashCashier cashCashier = new CashCashier();
		CatalogManager catalogManager = new CatalogManager();
        Location location = new Location();
        Vector listOpeningCashier =  PstCashCashier.listCashOpening(0, 0, 
                "CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_APPUSER_ID]+"='"+userId+"' "
                + "AND CSH."+PstCashCashier.fieldNames[PstCashCashier.FLD_SPVCLOSE_OID]+"='1'", "");   
        Vector openingCashier = new Vector(1,1);
        Vector listBillMain = new Vector(1,1);
        if(listOpeningCashier.size() != 0){
            openingCashier = (Vector) listOpeningCashier.get(0);
            if(openingCashier.size() != 0 ){
                cashCashier = (CashCashier) openingCashier.get(0);
                location = (Location) openingCashier.get(2);
            }
        }
		
		CatalogManager.statusProses += " Get Catalog Process Started<br>";
		//get count dulu lah ada data apa gak
		
 	String posUrl = PstSystemProperty.getValueByName("PROCHAIN_URL");
		try {
			JSONObject object = readJsonFromUrl(posUrl+"getDataCustomQuery?LOCATION_ID="
					+location.getOID()+"&DATAFOR=listAll");
			int sizeAll = object.getInt("sizeAll");
			if (sizeAll>0){
				CatalogManager.statusProses += " Connection Status : OK<br>";
				CatalogManager.statusProses += sizeAll+" data to be transfered<br>";
				int dataToFetch = 100;
				int x = 0;
				do {
					object = readJsonFromUrl(posUrl+"getDataCustomQuery?LOCATION_ID="
					+location.getOID()+"&DATAFOR=listQuery&FETCH_DATA="+dataToFetch);
					int size = object.getInt("size");
					if (size>0){
						JSONArray arr = object.getJSONArray("data");
						int number = 0;
						int eror = 0;
						String[] sql = new String[size];
						String[] strOid = new String[size];
						for (int i = 0; i < arr.length(); i++) {
							long oid = arr.getJSONObject(i).getLong("oid");
							String query = arr.getJSONObject(i).getString("query");
							sql[i] = query;
							strOid[i] = String.valueOf(oid);
						}
						try {
							int[] result = DBHandler.execUpdateBatch(sql);
							int i = 0;
							String inOid = "";
							for (int s : result){
								if (s < 0){
									LogSysHistory logSys = new LogSysHistory();
									logSys.setLogApplication("Cashier");
									logSys.setLogDetail(sql[i]);
									logSys.setLogDocumentId(0);
									logSys.setLogDocumentNumber("-");
									logSys.setLogLoginName("");
									logSys.setLogUpdateDate(new Date());
									logSys.setLogUserAction("Error Insert");
									try {
										PstLogSysHistory.insertExc(logSys);
									} catch (Exception ex){}
								}
								inOid+=strOid[i]+",";
								i++;
							}
							if (inOid.length()>0){
								inOid = inOid.substring(0, inOid.length() - 1);
							}
							JSONObject objectDelete = readJsonFromUrl(posUrl+"getDataCustomQuery?LOCATION_ID="
								+location.getOID()+"&DATAFOR=delete&OID="+inOid);
						} catch (Exception exc){

						}
					}
					x += dataToFetch;
					CatalogManager.statusData = "Process : "+x+"/"+sizeAll+" data<br>";
				} while (x <= sizeAll);
				CatalogManager.statusData += " Transfer completed <br>";
				CatalogManager.statusData += " Service will idle for 2 minute<br>";
			}
			
			
		} catch (Exception exc){
			
		}
		
	}*/
	
	public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
		  BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		  String jsonText = readAll(rd);
		  JSONObject json = new JSONObject(jsonText);
		  return json;
		} finally {
		  is.close();
		}
	  }
	
	private static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
		  sb.append((char) cp);
		}
		return sb.toString();
	}
	
	public void sendDispatchV3(){
		StockCheckManager.statusProses += " Process Upload Transfer Started <br>";
		String whereClause = PstMatDispatch.fieldNames[PstMatDispatch.FLD_DISPATCH_STATUS] + " = 5";
		Vector listDispatch = PstMatDispatch.list(0, 0, whereClause, "");
		if (listDispatch.size() > 0){
			StockCheckManager.statusProses += " "+listDispatch.size()+" found <br>";
			for (int i = 0; i < listDispatch.size(); i++){
				StockCheckManager.statusData = "Process : "+(i+1)+"/"+listDispatch.size()+" data<br>";
				List<String> query = new ArrayList<>();
				MatDispatch matDispatch = (MatDispatch) listDispatch.get(i);
				try {
					query.add(PstMatDispatch.getInsertSQL(matDispatch));
					query = sendDispatchItemV2(query, matDispatch.getOID());
					query = sendReceiveV2(query, matDispatch.getOID());
					
					try {
						int[] result = runQueryBatch(query);
						if (result[0]>0){
							matDispatch.setDispatchStatus(7);
							PstMatDispatch.updateExc(matDispatch);
						}
					} catch (Exception exc){
						
					}
				} catch (Exception exc){
					System.out.println("SendDispatchV2:"+exc.toString());
				}
			}
		} else {
			StockCheckManager.statusProses += " No Document found <br>";
		}
	}
	
	public int[] runQueryBatch(List<String> query){
		try {
			password = dataAddress[2];
		} catch (Exception e) {
		}
        Connection conn = null;
        Statement statement = null;
        int[] result = new int[query.size()];
		try {
			conn = DriverManager.getConnection(dbURL, username, password);
            statement = conn.createStatement();
			
			for (String s : query)
			{
				statement.addBatch(s);
			}
			
            result = statement.executeBatch();
			
		} catch(SQLException se){
			se.printStackTrace();
		} catch(Exception e){
		   //Handle errors for Class.forName
		   e.printStackTrace();
		} finally{
			try{
			   if(statement!=null)
				  conn.close();
			}catch(SQLException se){
			}// do nothing
			try{
			   if(conn!=null)
				  conn.close();
			}catch(SQLException se){
			   se.printStackTrace();
			}//end finally try
		 }
		return result;
	}
	
}
