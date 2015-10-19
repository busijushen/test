/**
 * Copyright (c) Hitachi Systems, Ltd。 2014。 All rights reserved。
 * Please read the associated COPYRIGHTS file for more details。
 *
 * THE SOFTWARE IS PROVIDED BY Hitachi Systems, Ltd。,
 * WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT。
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDER BE LIABLE FOR ANY
 * CLAIM, DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING
 * OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES。
 */


import java.io.IOException;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;
/**
 * 文字列を処理するユーティリティクラス<br />
 * 説明--->
 *
 * @author Hitachi systems
 *
 */
public class StringUtil {

    public static final String SINGLE_QUOTATION = "'";

    public static final String TWO_SINGLE_QUOTATION = "''";

    public static final String base = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!#$%&*+,-.:;<>=@_";

    /**
     * 空白チェック
     *
     * @param   str 入力文字列
     * @return  文字列がnullまたは空白の場合は、trueを返す。
     *           その他の場合は、falseを返す。
     * @since 01-00
     */
    public static boolean isBlank(String str) {

        if (str == null || "".equals(str.trim())) {
            return true;
        } else {
            return false;
        }
    }

    /** 半角英数字チェック

     ** 文字列が'A'-'Z','a'-'z','0'-'9'であるかどうかチェックする
     ** @param  String  入力文字列
     ** @return boolean 有効 true 無効 false
     **/

    public static boolean AlpChk(String myStr) {

        for (int i = 0; i < myStr.length(); i++) {

            char charData = myStr.charAt(i);
            if (((charData < 'A') || (charData > 'Z'))
                    && ((charData < 'a') || (charData > 'z'))
                    && ((charData < '0') || (charData > '9'))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 半角英数値と記号"-""_"チェック
     *
     ** 半角英数値と記号"-""_"以外の文字が入力されかどうかチェックする
     **
     * @param String
     *            入力文字列
     ** @return boolean 有効 true 無効 false
     **/

    public static boolean AlpLineChk(String myStr) {

        for (int i = 0; i < myStr.length(); i++) {

            char charData = myStr.charAt(i);
            if (!(((charData >= 'A') && (charData <= 'Z'))
                    || ((charData >= 'a') && (charData <= 'z'))
                    || ((charData >= '0') && (charData <= '9'))
                    || (charData == '-') || (charData == '_'))) {
                return false;
            }
        }
        return true;
    }

    /**
     * API用ﾊﾟｽﾜｰﾄﾞをﾁｪｯｸする。
     *
     * @param password 入力したﾊﾟｽﾜｰﾄﾞ
     * @return ﾁｪｯｸ結果
     */
    public static boolean apiPwdChk(String password) {


        for (int i = 0; i < password.length(); i++) {


            String charData = password.substring(i, i + 1);

            if (base.indexOf(charData) == -1) {

                return false;
            }
        }
        return true;
    }

    /**
     * 半角英数値と記号"_"チェック
     *
     ** 半角英数値と記号"_"以外の文字が入力されかどうかチェックする
     **
     * @param String
     *            入力文字列
     ** @return boolean 有効 true 無効 false
     **/

    public static boolean isSqlColumn(String myStr) {

        for (int i = 0; i < myStr.length(); i++) {

            char charData = myStr.charAt(i);
            if (!(((charData >= 'A') && (charData <= 'Z'))
                    || ((charData >= 'a') && (charData <= 'z'))
                    || ((charData >= '0') && (charData <= '9'))
                    || (charData == '_'))) {
                return false;
            }
        }
        return true;
    }

    /**
     * ' → "
     **/

    public static String changeSQL(String string) {

		String changed = null;
		// SINGLE_QUOTATIONがありません
		if (string.indexOf(SINGLE_QUOTATION) == -1) {
			changed = string;
		} else {
			changed = string.replace(SINGLE_QUOTATION, TWO_SINGLE_QUOTATION);
		}

		return changed;
	}
}
