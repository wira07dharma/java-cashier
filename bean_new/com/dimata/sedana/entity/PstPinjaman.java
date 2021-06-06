/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dimata.sedana.entity;

/**
 *
 * @author Regen
 */
import java.sql.*;
import com.dimata.util.lang.I_Language;
import com.dimata.common.db.*;
import com.dimata.qdep.entity.*;
import com.dimata.util.Command;
import com.dimata.util.Formater;
import java.util.Hashtable;
import java.util.Vector;
import org.json.JSONObject;

public class PstPinjaman extends DBHandler implements I_DBInterface, I_DBType, I_PersintentExc, I_Language {

  public static final String TBL_PINJAMAN = "aiso_pinjaman";
  public static final int FLD_PINJAMAN_ID = 0;
  public static final int FLD_ANGGOTA_ID = 1;
  public static final int FLD_TIPE_KREDIT_ID = 2;
  public static final int FLD_KELOMPOK_ID = 3;
  public static final int FLD_TGL_PENGAJUAN = 4;
  public static final int FLD_TGL_LUNAS = 5;
  public static final int FLD_JANGKA_WAKTU = 6;
  public static final int FLD_JATUH_TEMPO = 7;
  public static final int FLD_JUMLAH_PINJAMAN = 8;
  public static final int FLD_STATUS_PINJAMAN = 9;
  public static final int FLD_TGL_REALISASI = 10;
  public static final int FLD_JUMLAH_ANGSURAN = 11;
  public static final int FLD_KODE_KOLEKTIBILITAS = 12;
  public static final int FLD_KETERANGAN = 13;
  public static final int FLD_SUKU_BUNGA = 14;
  public static final int FLD_TIPE_BUNGA = 15;
  public static final int FLD_NO_KREDIT = 16;
  public static final int FLD_ASSIGN_TABUNGAN_ID = 17;
  public static final int FLD_ID_JENIS_SIMPANAN = 18;
  public static final int FLD_JENIS_TRANSAKSI_ID = 19;
  public static final int FLD_WAJIB_ID_JENIS_SIMPANAN = 20;
  public static final int FLD_WAJIB_VALUE = 21;
  public static final int FLD_WAJIB_VALUE_TYPE = 22;
  public static final int FLD_TIPE_JADWAL = 23;
  public static final int FLD_CASH_BILL_MAIN_ID = 24;
  public static final int FLD_DOWN_PAYMENT = 25;
  public static final int FLD_SISA_ANGSURAN = 26;
  public static final int FLD_ACCOUNT_OFFICER_ID = 27;
  public static final int FLD_COLLECTOR_ID = 28;
  public static final int FLD_JANGKA_WAKTU_ID = 29;
  public static final int FLD_LOKASI_PENAGIHAN = 30;

  public static String[] fieldNames = {
    "PINJAMAN_ID",//0
    "ANGGOTA_ID",//1
    "TIPE_KREDIT_ID",//2
    "KELOMPOK_ID",//3
    "TGL_PENGAJUAN",//4
    "TGL_LUNAS",//5
    "JANGKA_WAKTU",//6
    "JATUH_TEMPO",//7
    "JUMLAH_PINJAMAN",//8
    "STATUS_PINJAMAN",//9
    "TGL_REALISASI",//10
    "JUMLAH_ANGSURAN",//11
    "KODE_KOLEKTIBILITAS",//12
    "KETERANGAN",//13
    "SUKU_BUNGA",//14
    "TIPE_BUNGA",//15
    "NO_KREDIT",//16
    "ASSIGN_TABUNGAN_ID",
    "ID_JENIS_SIMPANAN",
    "JENIS_TRANSAKSI_ID",
    "WAJIB_ID_JENIS_SIMPANAN",
    "WAJIB_VALUE",
    "WAJIB_VALUE_TYPE",
    "TIPE_JADWAL",
    "CASH_BILL_MAIN_ID",
    "DOWN_PAYMENT",
    "SISA_ANGSURAN",
    "ACCOUNT_OFFICER_ID",
    "COLLECTOR_ID",
    "JANGKA_WAKTU_ID",
    "LOKASI_PENAGIHAN"
  };

  public static int[] fieldTypes = {
    TYPE_LONG + TYPE_PK + TYPE_ID,//0
    TYPE_LONG,//1
    TYPE_LONG,//2
    TYPE_LONG,//3
    TYPE_DATE,//4
    TYPE_DATE,//5
    TYPE_INT,//6
    TYPE_DATE,//7
    TYPE_FLOAT,//8
    TYPE_INT,//9
    TYPE_DATE,//10
    TYPE_FLOAT,//11
    TYPE_LONG,//12
    TYPE_STRING,//13
    TYPE_FLOAT,//14
    TYPE_INT,//15
    TYPE_STRING,//16
    TYPE_LONG,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_FLOAT,
    TYPE_INT,  
    TYPE_INT,
    TYPE_LONG,
    TYPE_FLOAT,
    TYPE_FLOAT,
    TYPE_LONG,
    TYPE_LONG,
    TYPE_LONG,
	TYPE_INT
  };

  public PstPinjaman() {
  }

  public PstPinjaman(int i) throws DBException {
    super(new PstPinjaman());
  }

  public PstPinjaman(String sOid) throws DBException {
    super(new PstPinjaman(0));
    if (!locate(sOid)) {
      throw new DBException(this, DBException.RECORD_NOT_FOUND);
    } else {
      return;
    }
  }

  public PstPinjaman(long lOid) throws DBException {
    super(new PstPinjaman(0));
    String sOid = "0";
    try {
      sOid = String.valueOf(lOid);
    } catch (Exception e) {
      throw new DBException(this, DBException.RECORD_NOT_FOUND);
    }
    if (!locate(sOid)) {
      throw new DBException(this, DBException.RECORD_NOT_FOUND);
    } else {
      return;
    }
  }

  public int getFieldSize() {
    return fieldNames.length;
  }

  public String getTableName() {
    return TBL_PINJAMAN;
  }

  public String[] getFieldNames() {
    return fieldNames;
  }

  public int[] getFieldTypes() {
    return fieldTypes;
  }

  public String getPersistentName() {
    return new PstPinjaman().getClass().getName();
  }

  public static Pinjaman fetchExc(long oid) throws DBException {
    try {
      Pinjaman entPinjaman = new Pinjaman();
      PstPinjaman pstPinjaman = new PstPinjaman(oid);
      entPinjaman.setOID(oid);
      entPinjaman.setAnggotaId(pstPinjaman.getlong(FLD_ANGGOTA_ID));
      entPinjaman.setTipeKreditId(pstPinjaman.getlong(FLD_TIPE_KREDIT_ID));
      entPinjaman.setKelompokId(pstPinjaman.getlong(FLD_KELOMPOK_ID));
      entPinjaman.setTglPengajuan(pstPinjaman.getDate(FLD_TGL_PENGAJUAN));
      entPinjaman.setTglLunas(pstPinjaman.getDate(FLD_TGL_LUNAS));
      entPinjaman.setJangkaWaktu(pstPinjaman.getInt(FLD_JANGKA_WAKTU));
      entPinjaman.setJatuhTempo(pstPinjaman.getDate(FLD_JATUH_TEMPO));
      entPinjaman.setJumlahPinjaman(pstPinjaman.getlong(FLD_JUMLAH_PINJAMAN));
      entPinjaman.setStatusPinjaman(pstPinjaman.getInt(FLD_STATUS_PINJAMAN));
      entPinjaman.setTglRealisasi(pstPinjaman.getDate(FLD_TGL_REALISASI));
      entPinjaman.setJumlahAngsuran(pstPinjaman.getdouble(FLD_JUMLAH_ANGSURAN));
      entPinjaman.setKodeKolektibilitas(pstPinjaman.getlong(FLD_KODE_KOLEKTIBILITAS));
      entPinjaman.setKeterangan(pstPinjaman.getString(FLD_KETERANGAN));
      entPinjaman.setSukuBunga(pstPinjaman.getdouble(FLD_SUKU_BUNGA));
      entPinjaman.setTipeBunga(pstPinjaman.getInt(FLD_TIPE_BUNGA));
      entPinjaman.setNoKredit(pstPinjaman.getString(FLD_NO_KREDIT));
      entPinjaman.setAssignTabunganId(pstPinjaman.getlong(FLD_ASSIGN_TABUNGAN_ID));
      entPinjaman.setIdJenisSimpanan(pstPinjaman.getlong(FLD_ID_JENIS_SIMPANAN));
      entPinjaman.setIdJenisTransaksi(pstPinjaman.getlong(FLD_JENIS_TRANSAKSI_ID));
      entPinjaman.setWajibIdJenisSimpanan(pstPinjaman.getlong(FLD_WAJIB_ID_JENIS_SIMPANAN));
      entPinjaman.setWajibValue(pstPinjaman.getdouble(FLD_WAJIB_VALUE));
      entPinjaman.setWajibValueType(pstPinjaman.getInt(FLD_WAJIB_VALUE_TYPE));
      entPinjaman.setTipeJadwal(pstPinjaman.getInt(FLD_TIPE_JADWAL));
        entPinjaman.setBillMainId(pstPinjaman.getlong(FLD_CASH_BILL_MAIN_ID));
        entPinjaman.setDownPayment(pstPinjaman.getdouble(FLD_DOWN_PAYMENT));
        entPinjaman.setSisaAngsuran(pstPinjaman.getdouble(FLD_SISA_ANGSURAN));
         entPinjaman.setAccountOfficerId(pstPinjaman.getlong(FLD_ACCOUNT_OFFICER_ID));
      entPinjaman.setCollectorId(pstPinjaman.getlong(FLD_COLLECTOR_ID));
        entPinjaman.setJangkaWaktuId(pstPinjaman.getlong(FLD_JANGKA_WAKTU_ID));
		entPinjaman.setLokasiPenagihan(pstPinjaman.getInt(FLD_LOKASI_PENAGIHAN));
      return entPinjaman;
    } catch (DBException dbe) {
      throw dbe;
    } catch (Exception e) {
      throw new DBException(new PstPinjaman(0), DBException.UNKNOWN);
    }
  }

  public long fetchExc(Entity entity) throws Exception {
    Pinjaman entPinjaman = fetchExc(entity.getOID());
    entity = (Entity) entPinjaman;
    return entPinjaman.getOID();
  }

  public static synchronized long updateExc(Pinjaman entPinjaman) throws DBException {
    try {
      if (entPinjaman.getOID() != 0) {
        PstPinjaman pstPinjaman = new PstPinjaman(entPinjaman.getOID());
        pstPinjaman.setLong(FLD_ANGGOTA_ID, entPinjaman.getAnggotaId());
        pstPinjaman.setLong(FLD_TIPE_KREDIT_ID, entPinjaman.getTipeKreditId());
        pstPinjaman.setLong(FLD_KELOMPOK_ID, entPinjaman.getKelompokId());
        pstPinjaman.setDate(FLD_TGL_PENGAJUAN, entPinjaman.getTglPengajuan());
        pstPinjaman.setDate(FLD_TGL_LUNAS, entPinjaman.getTglLunas());
        pstPinjaman.setInt(FLD_JANGKA_WAKTU, entPinjaman.getJangkaWaktu());
        pstPinjaman.setDate(FLD_JATUH_TEMPO, entPinjaman.getJatuhTempo());
        pstPinjaman.setDouble(FLD_JUMLAH_PINJAMAN, entPinjaman.getJumlahPinjaman());
        pstPinjaman.setInt(FLD_STATUS_PINJAMAN, entPinjaman.getStatusPinjaman());
        pstPinjaman.setDate(FLD_TGL_REALISASI, entPinjaman.getTglRealisasi());
        pstPinjaman.setDouble(FLD_JUMLAH_ANGSURAN, entPinjaman.getJumlahAngsuran());
        pstPinjaman.setLong(FLD_KODE_KOLEKTIBILITAS, entPinjaman.getKodeKolektibilitas());
        pstPinjaman.setString(FLD_KETERANGAN, entPinjaman.getKeterangan());
        pstPinjaman.setDouble(FLD_SUKU_BUNGA, entPinjaman.getSukuBunga());
        pstPinjaman.setInt(FLD_TIPE_BUNGA, entPinjaman.getTipeBunga());
        pstPinjaman.setString(FLD_NO_KREDIT, entPinjaman.getNoKredit());
        if (entPinjaman.getAssignTabunganId() != 0) {
            pstPinjaman.setLong(FLD_ASSIGN_TABUNGAN_ID, entPinjaman.getAssignTabunganId());
        }
        if (entPinjaman.getIdJenisSimpanan() != 0) {
            pstPinjaman.setLong(FLD_ID_JENIS_SIMPANAN, entPinjaman.getIdJenisSimpanan());
        }
        pstPinjaman.setLong(FLD_JENIS_TRANSAKSI_ID, entPinjaman.getIdJenisTransaksi());
        if (entPinjaman.getWajibIdJenisSimpanan() != 0) {
            pstPinjaman.setLong(FLD_WAJIB_ID_JENIS_SIMPANAN, entPinjaman.getWajibIdJenisSimpanan());
        }
        pstPinjaman.setDouble(FLD_WAJIB_VALUE, entPinjaman.getWajibValue());
        pstPinjaman.setInt(FLD_WAJIB_VALUE_TYPE, entPinjaman.getWajibValueType());
        pstPinjaman.setInt(FLD_TIPE_JADWAL, entPinjaman.getTipeJadwal());
        pstPinjaman.setLong(FLD_CASH_BILL_MAIN_ID, entPinjaman.getBillMainId());
        pstPinjaman.setDouble(FLD_DOWN_PAYMENT, entPinjaman.getDownPayment());
        pstPinjaman.setDouble(FLD_SISA_ANGSURAN, entPinjaman.getSisaAngsuran());
        pstPinjaman.setLong(FLD_ACCOUNT_OFFICER_ID, entPinjaman.getAccountOfficerId());
		pstPinjaman.setLong(FLD_COLLECTOR_ID, entPinjaman.getCollectorId());
        pstPinjaman.setLong(FLD_JANGKA_WAKTU_ID, entPinjaman.getJangkaWaktuId());
		pstPinjaman.setInt(FLD_LOKASI_PENAGIHAN, entPinjaman.getLokasiPenagihan());
        pstPinjaman.update();
        return entPinjaman.getOID();
      }
    } catch (DBException dbe) {
      throw dbe;
    } catch (Exception e) {
      throw new DBException(new PstPinjaman(0), DBException.UNKNOWN);
    }
    return 0;
  }

  public long updateExc(Entity entity) throws Exception {
    return updateExc((Pinjaman) entity);
  }

  public static synchronized long deleteExc(long oid) throws DBException {
    try {
      PstPinjaman pstPinjaman = new PstPinjaman(oid);
      pstPinjaman.delete();
    } catch (DBException dbe) {
      throw dbe;
    } catch (Exception e) {
      throw new DBException(new PstPinjaman(0), DBException.UNKNOWN);
    }
    return oid;
  }

  public long deleteExc(Entity entity) throws Exception {
    if (entity == null) {
      throw new DBException(this, DBException.RECORD_NOT_FOUND);
    }
    return deleteExc(entity.getOID());
  }

  public static synchronized long insertExc(Pinjaman entPinjaman) throws DBException {
    try {
      PstPinjaman pstPinjaman = new PstPinjaman(0);
      pstPinjaman.setLong(FLD_ANGGOTA_ID, entPinjaman.getAnggotaId());
      pstPinjaman.setLong(FLD_TIPE_KREDIT_ID, entPinjaman.getTipeKreditId());
      pstPinjaman.setLong(FLD_KELOMPOK_ID, entPinjaman.getKelompokId());
      pstPinjaman.setDate(FLD_TGL_PENGAJUAN, entPinjaman.getTglPengajuan());
      pstPinjaman.setDate(FLD_TGL_LUNAS, entPinjaman.getTglLunas());
      pstPinjaman.setInt(FLD_JANGKA_WAKTU, entPinjaman.getJangkaWaktu());
      pstPinjaman.setDate(FLD_JATUH_TEMPO, entPinjaman.getJatuhTempo());
      pstPinjaman.setDouble(FLD_JUMLAH_PINJAMAN, entPinjaman.getJumlahPinjaman());
      pstPinjaman.setInt(FLD_STATUS_PINJAMAN, entPinjaman.getStatusPinjaman());
      pstPinjaman.setDate(FLD_TGL_REALISASI, entPinjaman.getTglRealisasi());
      pstPinjaman.setDouble(FLD_JUMLAH_ANGSURAN, entPinjaman.getJumlahAngsuran());
      pstPinjaman.setLong(FLD_KODE_KOLEKTIBILITAS, entPinjaman.getKodeKolektibilitas());
      pstPinjaman.setString(FLD_KETERANGAN, entPinjaman.getKeterangan());
      pstPinjaman.setDouble(FLD_SUKU_BUNGA, entPinjaman.getSukuBunga());
      pstPinjaman.setInt(FLD_TIPE_BUNGA, entPinjaman.getTipeBunga());
      pstPinjaman.setString(FLD_NO_KREDIT, entPinjaman.getNoKredit());
      if (entPinjaman.getAssignTabunganId() != 0) {
        pstPinjaman.setLong(FLD_ASSIGN_TABUNGAN_ID, entPinjaman.getAssignTabunganId());
      }
      if (entPinjaman.getIdJenisSimpanan() != 0) {
        pstPinjaman.setLong(FLD_ID_JENIS_SIMPANAN, entPinjaman.getIdJenisSimpanan());
      }
      pstPinjaman.setLong(FLD_JENIS_TRANSAKSI_ID, entPinjaman.getIdJenisTransaksi());
      if (entPinjaman.getWajibIdJenisSimpanan() != 0) {
        pstPinjaman.setLong(FLD_WAJIB_ID_JENIS_SIMPANAN, entPinjaman.getWajibIdJenisSimpanan());
      }
      pstPinjaman.setDouble(FLD_WAJIB_VALUE, entPinjaman.getWajibValue());
      pstPinjaman.setInt(FLD_WAJIB_VALUE_TYPE, entPinjaman.getWajibValueType());
      pstPinjaman.setInt(FLD_TIPE_JADWAL, entPinjaman.getTipeJadwal());
        pstPinjaman.setLong(FLD_CASH_BILL_MAIN_ID, entPinjaman.getBillMainId());
        pstPinjaman.setDouble(FLD_DOWN_PAYMENT, entPinjaman.getDownPayment());
        pstPinjaman.setDouble(FLD_SISA_ANGSURAN, entPinjaman.getSisaAngsuran());
         pstPinjaman.setLong(FLD_ACCOUNT_OFFICER_ID, entPinjaman.getAccountOfficerId());
	  pstPinjaman.setLong(FLD_COLLECTOR_ID, entPinjaman.getCollectorId());
        pstPinjaman.setLong(FLD_JANGKA_WAKTU_ID, entPinjaman.getJangkaWaktuId());
		pstPinjaman.setInt(FLD_LOKASI_PENAGIHAN, entPinjaman.getLokasiPenagihan());
      pstPinjaman.insert();
      entPinjaman.setOID(pstPinjaman.getlong(FLD_PINJAMAN_ID));
    } catch (DBException dbe) {
      throw dbe;
    } catch (Exception e) {
      throw new DBException(new PstPinjaman(0), DBException.UNKNOWN);
    }
    return entPinjaman.getOID();
  }

  public long insertExc(Entity entity) throws Exception {
    return insertExc((Pinjaman) entity);
  }

  public static void resultToObject(ResultSet rs, Pinjaman entPinjaman) {
    try {
      entPinjaman.setOID(rs.getLong(PstPinjaman.fieldNames[PstPinjaman.FLD_PINJAMAN_ID]));
      entPinjaman.setAnggotaId(rs.getLong(PstPinjaman.fieldNames[PstPinjaman.FLD_ANGGOTA_ID]));
      entPinjaman.setTipeKreditId(rs.getLong(PstPinjaman.fieldNames[PstPinjaman.FLD_TIPE_KREDIT_ID]));
      entPinjaman.setKelompokId(rs.getLong(PstPinjaman.fieldNames[PstPinjaman.FLD_KELOMPOK_ID]));
      entPinjaman.setTglPengajuan(rs.getDate(PstPinjaman.fieldNames[PstPinjaman.FLD_TGL_PENGAJUAN]));
      entPinjaman.setTglLunas(rs.getDate(PstPinjaman.fieldNames[PstPinjaman.FLD_TGL_LUNAS]));
      entPinjaman.setJangkaWaktu(rs.getInt(PstPinjaman.fieldNames[PstPinjaman.FLD_JANGKA_WAKTU]));
      entPinjaman.setJatuhTempo(rs.getDate(PstPinjaman.fieldNames[PstPinjaman.FLD_JATUH_TEMPO]));
      entPinjaman.setJumlahPinjaman(rs.getDouble(PstPinjaman.fieldNames[PstPinjaman.FLD_JUMLAH_PINJAMAN]));
      entPinjaman.setStatusPinjaman(rs.getInt(PstPinjaman.fieldNames[PstPinjaman.FLD_STATUS_PINJAMAN]));
      entPinjaman.setTglRealisasi(rs.getDate(PstPinjaman.fieldNames[PstPinjaman.FLD_TGL_REALISASI]));
      entPinjaman.setJumlahAngsuran(rs.getDouble(PstPinjaman.fieldNames[PstPinjaman.FLD_JUMLAH_ANGSURAN]));
      entPinjaman.setKodeKolektibilitas(rs.getLong(PstPinjaman.fieldNames[PstPinjaman.FLD_KODE_KOLEKTIBILITAS]));
      entPinjaman.setKeterangan(rs.getString(PstPinjaman.fieldNames[PstPinjaman.FLD_KETERANGAN]));
      entPinjaman.setSukuBunga(rs.getDouble(PstPinjaman.fieldNames[PstPinjaman.FLD_SUKU_BUNGA]));
      entPinjaman.setTipeBunga(rs.getInt(PstPinjaman.fieldNames[PstPinjaman.FLD_TIPE_BUNGA]));
      entPinjaman.setNoKredit(rs.getString(PstPinjaman.fieldNames[PstPinjaman.FLD_NO_KREDIT]));
      entPinjaman.setAssignTabunganId(rs.getLong(PstPinjaman.fieldNames[PstPinjaman.FLD_ASSIGN_TABUNGAN_ID]));
      entPinjaman.setIdJenisSimpanan(rs.getLong(PstPinjaman.fieldNames[PstPinjaman.FLD_ID_JENIS_SIMPANAN]));
      entPinjaman.setIdJenisTransaksi(rs.getLong(PstPinjaman.fieldNames[PstPinjaman.FLD_JENIS_TRANSAKSI_ID]));
      entPinjaman.setWajibIdJenisSimpanan(rs.getLong(fieldNames[FLD_WAJIB_ID_JENIS_SIMPANAN]));
      entPinjaman.setWajibValue(rs.getDouble(fieldNames[FLD_WAJIB_VALUE]));
      entPinjaman.setWajibValueType(rs.getInt(fieldNames[FLD_WAJIB_VALUE_TYPE]));
      entPinjaman.setTipeJadwal(rs.getInt(fieldNames[FLD_TIPE_JADWAL]));
	  entPinjaman.setBillMainId(rs.getLong(fieldNames[FLD_CASH_BILL_MAIN_ID]));
	  entPinjaman.setDownPayment(rs.getDouble(fieldNames[FLD_DOWN_PAYMENT]));
	  entPinjaman.setSisaAngsuran(rs.getDouble(fieldNames[FLD_SISA_ANGSURAN]));
          entPinjaman.setAccountOfficerId(rs.getLong(fieldNames[FLD_ACCOUNT_OFFICER_ID]));
	  entPinjaman.setCollectorId(rs.getLong(fieldNames[FLD_COLLECTOR_ID]));
          entPinjaman.setJangkaWaktuId(rs.getLong(fieldNames[FLD_JANGKA_WAKTU_ID]));
		  entPinjaman.setLokasiPenagihan(rs.getInt(fieldNames[FLD_LOKASI_PENAGIHAN])); 
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public static Vector listAll() {
    return list(0, 500, "", "");
  }

  public static Vector list(int limitStart, int recordToGet, String whereClause, String order) {
    Vector lists = new Vector();
    DBResultSet dbrs = null;
    try {
      String sql = "SELECT * FROM " + TBL_PINJAMAN;
      if (whereClause != null && whereClause.length() > 0) {
        sql = sql + " WHERE " + whereClause;
      }
      if (order != null && order.length() > 0) {
        sql = sql + " ORDER BY " + order;
      }
      if (limitStart == 0 && recordToGet == 0) {
        sql = sql + "";
      } else {
        sql = sql + " LIMIT " + limitStart + "," + recordToGet;
      }
      dbrs = DBHandler.execQueryResult(sql);
      ResultSet rs = dbrs.getResultSet();
      while (rs.next()) {
        Pinjaman entPinjaman = new Pinjaman();
        resultToObject(rs, entPinjaman);
        lists.add(entPinjaman);
      }
      rs.close();
      return lists;
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      DBResultSet.close(dbrs);
    }
    return new Vector();
  }

 
  
  
 
  public static boolean checkOID(long entPinjamanId) {
    DBResultSet dbrs = null;
    boolean result = false;
    try {
      String sql = "SELECT * FROM " + TBL_PINJAMAN + " WHERE "
              + PstPinjaman.fieldNames[PstPinjaman.FLD_PINJAMAN_ID] + " = " + entPinjamanId;
      dbrs = DBHandler.execQueryResult(sql);
      ResultSet rs = dbrs.getResultSet();
      while (rs.next()) {
        result = true;
      }
      rs.close();
    } catch (Exception e) {
      System.out.println("err : " + e.toString());
    } finally {
      DBResultSet.close(dbrs);
      return result;
    }
  }

  public static int getCount(String whereClause) {
    DBResultSet dbrs = null;
    try {
      String sql = "SELECT COUNT(" + PstPinjaman.fieldNames[PstPinjaman.FLD_PINJAMAN_ID] + ") FROM " + TBL_PINJAMAN;
      if (whereClause != null && whereClause.length() > 0) {
        sql = sql + " WHERE " + whereClause;
      }
      dbrs = DBHandler.execQueryResult(sql);
      ResultSet rs = dbrs.getResultSet();
      int count = 0;
      while (rs.next()) {
        count = rs.getInt(1);
      }
      rs.close();
      return count;
    } catch (Exception e) {
      return 0;
    } finally {
      DBResultSet.close(dbrs);
    }
  }

  
 
  public static int findLimitStart(long oid, int recordToGet, String whereClause, String orderClause) {
    int size = getCount(whereClause);
    int start = 0;
    boolean found = false;
    for (int i = 0; (i < size) && !found; i = i + recordToGet) {
      Vector list = list(i, recordToGet, whereClause, orderClause);
      start = i;
      if (list.size() > 0) {
        for (int ls = 0; ls < list.size(); ls++) {
          Pinjaman entPinjaman = (Pinjaman) list.get(ls);
          if (oid == entPinjaman.getOID()) {
            found = true;
          }
        }
      }
    }
    if ((start >= size) && (size > 0)) {
      start = start - recordToGet;
    }
    return start;
  }

  public static int findLimitCommand(int start, int recordToGet, int vectSize) {
    int cmd = Command.LIST;
    int mdl = vectSize % recordToGet;
    vectSize = vectSize + (recordToGet - mdl);
    if (start == 0) {
      cmd = Command.FIRST;
    } else {
      if (start == (vectSize - recordToGet)) {
        cmd = Command.LAST;
      } else {
        start = start + recordToGet;
        if (start <= (vectSize - recordToGet)) {
          cmd = Command.NEXT;
          System.out.println("next.......................");
        } else {
          start = start - recordToGet;
          if (start > 0) {
            cmd = Command.PREV;
            System.out.println("prev.......................");
          }
        }
      }
    }
    return cmd;
  }

  
  public static boolean isLunas(long oidPinjaman) {
    boolean lunas = false;
    String sql = "SELECT COUNT(0) FROM ( SELECT j.`JADWAL_ANGSURAN_ID`, j.`JUMLAH_ANGSURAN`, SUM(a.`JUMLAH_ANGSURAN`), COALESCE(j.`JUMLAH_ANGSURAN`-SUM(a.`JUMLAH_ANGSURAN`),1) sigma FROM `sedana_jadwal_angsuran` j LEFT JOIN aiso_angsuran a USING(JADWAL_ANGSURAN_ID) WHERE j.`PINJAMAN_ID`="+oidPinjaman+" GROUP BY j.`JADWAL_ANGSURAN_ID` ) AS `table` WHERE `table`.`sigma`>0";
    DBResultSet dbrs = null;
    try {
      dbrs = DBHandler.execQueryResult(sql);
      ResultSet rs = dbrs.getResultSet();
      if (rs.next()) {
        lunas = !(rs.getLong(1)>0);
      }
      rs.close();
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      DBResultSet.close(dbrs);
    }
    return lunas;
  }
   public static JSONObject fetchJSON(long oid){
      JSONObject object = new JSONObject();
      try {
         Pinjaman pinjaman = PstPinjaman.fetchExc(oid);
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_PINJAMAN_ID], pinjaman.getOID());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_ANGGOTA_ID], pinjaman.getAnggotaId());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_TIPE_KREDIT_ID], pinjaman.getTipeKreditId());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_KELOMPOK_ID], pinjaman.getKelompokId());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_TGL_PENGAJUAN], pinjaman.getTglPengajuan());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_TGL_LUNAS], pinjaman.getTglLunas());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_JANGKA_WAKTU], pinjaman.getJangkaWaktu());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_JATUH_TEMPO], pinjaman.getJatuhTempo());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_JUMLAH_PINJAMAN], pinjaman.getJumlahPinjaman());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_STATUS_PINJAMAN], pinjaman.getStatusPinjaman());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_TGL_REALISASI], pinjaman.getTglRealisasi());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_JUMLAH_ANGSURAN], pinjaman.getJumlahAngsuran());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_KODE_KOLEKTIBILITAS], pinjaman.getKodeKolektibilitas());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_KETERANGAN], pinjaman.getKeterangan());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_SUKU_BUNGA], pinjaman.getSukuBunga());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_TIPE_BUNGA], pinjaman.getTipeBunga());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_NO_KREDIT], pinjaman.getNoKredit());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_ASSIGN_TABUNGAN_ID], pinjaman.getAssignTabunganId());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_ID_JENIS_SIMPANAN], pinjaman.getIdJenisSimpanan());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_JENIS_TRANSAKSI_ID], pinjaman.getIdJenisTransaksi());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_WAJIB_ID_JENIS_SIMPANAN], pinjaman.getWajibIdJenisSimpanan());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_WAJIB_VALUE], pinjaman.getWajibValue());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_WAJIB_VALUE_TYPE], pinjaman.getWajibValueType());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_TIPE_JADWAL], pinjaman.getTipeJadwal());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_CASH_BILL_MAIN_ID], pinjaman.getBillMainId());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_DOWN_PAYMENT], pinjaman.getDownPayment());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_SISA_ANGSURAN], pinjaman.getSisaAngsuran());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_COLLECTOR_ID], pinjaman.getCollectorId());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_ACCOUNT_OFFICER_ID], pinjaman.getAccountOfficerId());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_JANGKA_WAKTU_ID], pinjaman.getJangkaWaktuId());
         object.put(PstPinjaman.fieldNames[PstPinjaman.FLD_LOKASI_PENAGIHAN], pinjaman.getLokasiPenagihan());
      }catch(Exception exc){}
      return object;
   }
      public static long syncExc(JSONObject jSONObject){
      long oid = 0;
      if (jSONObject != null){
       oid = jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_PINJAMAN_ID],0);
         if (oid > 0){
          Pinjaman pinjaman = new Pinjaman();
          pinjaman.setOID(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_PINJAMAN_ID],0));
          pinjaman.setAnggotaId(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_ANGGOTA_ID],0));
          pinjaman.setTipeKreditId(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_TIPE_KREDIT_ID],0));
          pinjaman.setKelompokId(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_KELOMPOK_ID],0));
          pinjaman.setTglPengajuan(Formater.formatDate(jSONObject.optString(PstPinjaman.fieldNames[PstPinjaman.FLD_TGL_PENGAJUAN],"0000-00-00"),"yyyy-MM-dd"));
          pinjaman.setTglLunas(Formater.formatDate(jSONObject.optString(PstPinjaman.fieldNames[PstPinjaman.FLD_TGL_LUNAS],"0000-00-00"),"yyyy-MM-dd"));
          pinjaman.setJangkaWaktu(jSONObject.optInt(PstPinjaman.fieldNames[PstPinjaman.FLD_JANGKA_WAKTU],0));
          pinjaman.setJatuhTempo(Formater.formatDate(jSONObject.optString(PstPinjaman.fieldNames[PstPinjaman.FLD_JATUH_TEMPO],"0000-00-00"),"yyyy-MM-dd"));
          pinjaman.setJumlahPinjaman(jSONObject.optDouble(PstPinjaman.fieldNames[PstPinjaman.FLD_JUMLAH_PINJAMAN],0));
          pinjaman.setStatusPinjaman(jSONObject.optInt(PstPinjaman.fieldNames[PstPinjaman.FLD_STATUS_PINJAMAN],0));
          pinjaman.setTglRealisasi(Formater.formatDate(jSONObject.optString(PstPinjaman.fieldNames[PstPinjaman.FLD_TGL_REALISASI],"0000-00-00"), "yyyy-MM-dd"));
          pinjaman.setJumlahAngsuran(Float.valueOf(jSONObject.optString(PstPinjaman.fieldNames[PstPinjaman.FLD_JUMLAH_ANGSURAN],"")));
          pinjaman.setKodeKolektibilitas(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_KODE_KOLEKTIBILITAS],0));
          pinjaman.setKeterangan(jSONObject.optString(PstPinjaman.fieldNames[PstPinjaman.FLD_KETERANGAN], ""));
          pinjaman.setSukuBunga(jSONObject.optDouble(PstPinjaman.fieldNames[PstPinjaman.FLD_SUKU_BUNGA],0));
          pinjaman.setTipeBunga(jSONObject.optInt(PstPinjaman.fieldNames[PstPinjaman.FLD_TIPE_BUNGA],0));
          pinjaman.setNoKredit(jSONObject.optString(PstPinjaman.fieldNames[PstPinjaman.FLD_NO_KREDIT], ""));
          pinjaman.setAssignTabunganId(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_ASSIGN_TABUNGAN_ID],0));
          pinjaman.setIdJenisSimpanan(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_ID_JENIS_SIMPANAN],0));
          pinjaman.setIdJenisTransaksi(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_JENIS_TRANSAKSI_ID],0));
          pinjaman.setWajibIdJenisSimpanan(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_WAJIB_ID_JENIS_SIMPANAN],0));
          pinjaman.setWajibValue(Float.valueOf(jSONObject.optString(PstPinjaman.fieldNames[PstPinjaman.FLD_WAJIB_VALUE],"")));
          pinjaman.setWajibValueType(jSONObject.optInt(PstPinjaman.fieldNames[PstPinjaman.FLD_WAJIB_VALUE_TYPE],0));
          pinjaman.setTipeJadwal(jSONObject.optInt(PstPinjaman.fieldNames[PstPinjaman.FLD_TIPE_JADWAL],0));
          pinjaman.setBillMainId(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_CASH_BILL_MAIN_ID],0));
          pinjaman.setDownPayment(jSONObject.optDouble(PstPinjaman.fieldNames[PstPinjaman.FLD_DOWN_PAYMENT],0));
          pinjaman.setSisaAngsuran(jSONObject.optDouble(PstPinjaman.fieldNames[PstPinjaman.FLD_SISA_ANGSURAN],0));
          pinjaman.setAccountOfficerId(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_ACCOUNT_OFFICER_ID],0));
          pinjaman.setJangkaWaktuId(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_JANGKA_WAKTU_ID],0));
          pinjaman.setCollectorId(jSONObject.optLong(PstPinjaman.fieldNames[PstPinjaman.FLD_COLLECTOR_ID],0));
          pinjaman.setLokasiPenagihan(jSONObject.optInt(PstPinjaman.fieldNames[PstPinjaman.FLD_LOKASI_PENAGIHAN],0));
         boolean checkOidPinjaman = PstPinjaman.checkOID(oid);
          try{
            if(checkOidPinjaman){
               PstPinjaman.updateExc(pinjaman);
            }else{
               PstPinjaman.insertByOid(pinjaman);
            }
         }catch(Exception exc){}
         }
      }
   return oid;
   }



   public static long insertByOid(Pinjaman pinjaman) throws DBException {
      try {
         PstPinjaman pstPinjaman = new PstPinjaman(0);
         pstPinjaman.setLong(FLD_PINJAMAN_ID, pinjaman.getOID());
         pstPinjaman.setLong(FLD_ANGGOTA_ID, pinjaman.getAnggotaId());
         pstPinjaman.setLong(FLD_TIPE_KREDIT_ID, pinjaman.getTipeKreditId());
         pstPinjaman.setLong(FLD_KELOMPOK_ID, pinjaman.getKelompokId());
         pstPinjaman.setDate(FLD_TGL_PENGAJUAN, pinjaman.getTglPengajuan());
         pstPinjaman.setDate(FLD_TGL_LUNAS, pinjaman.getTglLunas());
         pstPinjaman.setInt(FLD_JANGKA_WAKTU, pinjaman.getJangkaWaktu());
         pstPinjaman.setDate(FLD_JATUH_TEMPO, pinjaman.getJatuhTempo());
         pstPinjaman.setDouble(FLD_JUMLAH_PINJAMAN, pinjaman.getJumlahPinjaman());
         pstPinjaman.setInt(FLD_STATUS_PINJAMAN, pinjaman.getStatusPinjaman());
         pstPinjaman.setDate(FLD_TGL_REALISASI, pinjaman.getTglRealisasi());
         pstPinjaman.setDouble(FLD_JUMLAH_ANGSURAN, pinjaman.getJumlahAngsuran());
         pstPinjaman.setLong(FLD_KODE_KOLEKTIBILITAS, pinjaman.getKodeKolektibilitas());
         pstPinjaman.setString(FLD_KETERANGAN, pinjaman.getKeterangan());
         pstPinjaman.setDouble(FLD_SUKU_BUNGA, pinjaman.getSukuBunga());
         pstPinjaman.setInt(FLD_TIPE_BUNGA, pinjaman.getTipeBunga());
         pstPinjaman.setString(FLD_NO_KREDIT, pinjaman.getNoKredit());
         pstPinjaman.setLong(FLD_ASSIGN_TABUNGAN_ID, pinjaman.getAssignTabunganId());
         pstPinjaman.setLong(FLD_ID_JENIS_SIMPANAN, pinjaman.getIdJenisSimpanan());
         pstPinjaman.setLong(FLD_JENIS_TRANSAKSI_ID, pinjaman.getIdJenisTransaksi());
         pstPinjaman.setLong(FLD_WAJIB_ID_JENIS_SIMPANAN, pinjaman.getWajibIdJenisSimpanan());
         pstPinjaman.setDouble(FLD_WAJIB_VALUE, pinjaman.getWajibValue());
         pstPinjaman.setInt(FLD_WAJIB_VALUE_TYPE, pinjaman.getWajibValueType());
         pstPinjaman.setInt(FLD_TIPE_JADWAL, pinjaman.getTipeJadwal());
         pstPinjaman.setLong(FLD_CASH_BILL_MAIN_ID, pinjaman.getBillMainId());
         pstPinjaman.setDouble(FLD_DOWN_PAYMENT, pinjaman.getDownPayment());
         pstPinjaman.setDouble(FLD_SISA_ANGSURAN, pinjaman.getSisaAngsuran());
         pstPinjaman.setLong(FLD_ACCOUNT_OFFICER_ID, pinjaman.getAccountOfficerId());
	  pstPinjaman.setLong(FLD_COLLECTOR_ID, pinjaman.getCollectorId());
        pstPinjaman.setLong(FLD_JANGKA_WAKTU_ID, pinjaman.getJangkaWaktuId());
		pstPinjaman.setInt(FLD_LOKASI_PENAGIHAN, pinjaman.getLokasiPenagihan());
         pstPinjaman.insertByOid(pinjaman.getOID());
      } catch (DBException dbe) {
         throw dbe;
      } catch (Exception e) {
         throw new DBException(new PstPinjaman(0), DBException.UNKNOWN);
      }
      return pinjaman.getOID();
   }
}
