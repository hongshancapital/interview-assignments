//
//  TRXString.swift
//  STBaseProject
//
//  Created by stack on 2017/10/23.
//  Copyright © 2017年 ST. All rights reserved.
//

import UIKit
import Foundation

public extension String {
    func st_jsonStringToPrettyPrintedJson(jsonString: String?) -> String {
        if let str = jsonString {
            let jsonData = str.data(using: String.Encoding.utf8)!
            let jsonObject: AnyObject = try! JSONSerialization.jsonObject(with: jsonData, options: []) as AnyObject
            let prettyJsonData = try! JSONSerialization.data(withJSONObject: jsonObject, options: JSONSerialization.WritingOptions.prettyPrinted)
            let prettyPrintedJson = NSString(data: prettyJsonData, encoding: String.Encoding.utf8.rawValue)!
            return prettyPrintedJson as String
        }
        return ""
    }
    
    func st_dictToJSON(dict: NSDictionary) -> String {
        if dict.count < 1 {
            return ""
        }
        let data: NSData! = try! JSONSerialization.data(withJSONObject: dict, options: []) as NSData
        let jsonStr: String = String.init(data: data! as Data, encoding: String.Encoding(rawValue: String.Encoding.utf8.rawValue)) ?? ""
        return jsonStr
    }
}

public extension String {
    static func st_returnStr(object: Any) -> String {
        var cnt = ""
        if let obj = object as? NSNumber {
            cnt = String.init(format: "%@", obj)
        } else if let obj = object as? String {
            if obj.contains("null") || obj.contains("nan") || obj.contains("nil") {
                cnt = ""
            } else {
                cnt = obj
            }
        } else if let obj = object as? Bool {
            if obj {
                cnt = "1"
            } else {
                cnt = "0"
            }
        } else if let obj = object as? STJSONValue {
            switch obj {
            case .bool(let value):
                if value {
                    cnt = "1"
                } else {
                    cnt = "0"
                }
                break
            case .int(let value):
                cnt = String(value)
                break
            case .double(let value):
                cnt = String(value)
                break
            case .string(let value):
                cnt = value
                break
            default:
                break
            }
        }
        return cnt
    }
    
    func st_returnStrWidth(font: UIFont) -> CGFloat {
        let normalText: NSString = self as NSString
        let size = CGSize(width: 999, height: 1000)
        let attributes = [NSAttributedString.Key.font: font]
        let stringSize = normalText.boundingRect(with: size, options: .usesLineFragmentOrigin, attributes: attributes, context:nil).size
        return CGFloat(ceilf(Float(stringSize.width)))
    }
}

public extension String {
    func st_divideAmount() -> String {
        let numFormatter = NumberFormatter()
        let string = String.st_returnStr(object: self)
        numFormatter.formatterBehavior = NumberFormatter.Behavior.behavior10_4
        numFormatter.numberStyle = NumberFormatter.Style.decimal
        numFormatter.maximumFractionDigits = 6
        numFormatter.locale = Locale(identifier: "en_US")
        let numString = numFormatter.string(from: NSNumber.init(floatLiteral: Double(string) ?? 0))
        return numString ?? string
    }
    
    func st_stringToDouble(string: String) -> Double {
        let formatter = NumberFormatter()
        formatter.locale = Locale.current
        formatter.decimalSeparator = "."
        if let result = formatter.number(from: string) {
            return result.doubleValue
        } else {
            formatter.decimalSeparator = ","
            if let result = formatter.number(from: string) {
                return result.doubleValue
            }
        }
        return 0
    }
    
    var doubleValue: Double {
        return st_stringToDouble(string: self)
    }
    
    func st_convertToCurrency(style: NumberFormatter.Style) -> String {
        let number = NSDecimalNumber.init(string: self)
        let detail = NumberFormatter.localizedString(from: number, number: style)
        return detail
    }
}

public extension String {
    func st_attributed(originStr: String, originStrColor: UIColor, originStrFont: UIFont, replaceStrs: [String], replaceStrColors: [UIColor], replaceStrFonts: [UIFont]) -> NSMutableAttributedString {
        let str = NSMutableAttributedString.init(string: originStr)
        str.addAttributes([NSAttributedString.Key.font : originStrFont, NSAttributedString.Key.foregroundColor: originStrColor], range: NSRange.init(location: 0, length: str.length))
        for (i, replaceStr) in replaceStrs.enumerated() {
            if originStr.contains(replaceStr) {
                let range = originStr.range(of: replaceStr)!
                let location = originStr.distance(from: originStr.startIndex, to: range.lowerBound)
                let length = originStr.distance(from: range.lowerBound, to: range.upperBound)
                
                var font = UIFont.st_systemFont(ofSize: 14)
                if replaceStrFonts.count > i {
                    font = replaceStrFonts[i]
                }
                
                var color = UIColor.clear
                if replaceStrColors.count > i {
                    color = replaceStrColors[i]
                }
                str.addAttributes([NSAttributedString.Key.font: font, NSAttributedString.Key.foregroundColor: color], range: NSRange.init(location: location, length: length))
            }
        }
        return str
    }
}

public extension String {
    func st_parameterWithURL() -> Dictionary<String, Any> {
        var parmDict: Dictionary<String, String> = Dictionary<String, String>()
        if self.count > 0 {
            let urlComponents = NSURLComponents.init(string: self)
            if let queryItems = urlComponents?.queryItems {
                for item in queryItems {
                    if item.name.count > 0 {
                        parmDict[item.name] = item.value
                    }
                }
            }
        }
        return parmDict
    }
    
    func st_pasteboardWithString(pasteboardString: String) -> Void {
        let pasteboard = UIPasteboard.general
        pasteboard.string = pasteboardString
    }
}
