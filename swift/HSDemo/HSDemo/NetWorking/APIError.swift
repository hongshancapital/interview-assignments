//
//  APIError.swift
//  SwitUI实战
//

import Foundation

enum APIError: Error, CustomStringConvertible {
    case badURL
    case paramsError
    case badResponse(statusCode: Int)
    case url(URLError?)
    case parsing(DecodingError?)
    case otherError(Error?)
    case unknown

    var localizedDescription: String {
        // user feedback
        switch self {
        case .badURL, .parsing, .unknown:
            return "Sorry, something went wrong."
        case .paramsError:
            return "Sorry,please check the params."
        case .badResponse:
            return "Sorry, the connection to our server failed."
        case .url(let error):
            return error?.localizedDescription ?? "Something went wrong."
        case .otherError(let error):
            return error?.localizedDescription ?? "Something went wrong."
        }
    }

    var description: String {
        // info for debugging
        switch self {
        case .unknown: return "unknown error"
        case .badURL: return "invalid URL"
        case .paramsError: return "wrong paras"
        case .url(let error):
            return error?.localizedDescription ?? "url session error"
        case .otherError(let error):
            return error?.localizedDescription ?? "Something went wrong."
        case .parsing(let error):
            return "parsing error \(error?.localizedDescription ?? "")"
        case .badResponse(statusCode: let statusCode):
            return "bad response with status code \(statusCode)"
        }
    }
}
