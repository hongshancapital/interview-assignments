//
//  AppState.swift
//  SwiftUIJustPlay
//
//  Created by wangrenzhu2021 on 2022/5/8.
//

import Foundation
import SwiftUIFlux

fileprivate var savePath: URL!
let encoder = JSONEncoder()
let decoder: JSONDecoder = {
    let decoder = JSONDecoder()
    decoder.keyDecodingStrategy = .convertFromSnakeCase
    return decoder
}()

struct AppState: FluxState, Codable {
    var homeState: HomeState
    
    
    init() {
        do {
            let icloudDirectory = FileManager.default.url(forUbiquityContainerIdentifier: nil)
            let documentDirectory = try FileManager.default.url(for: .documentDirectory,
                                                                in: .userDomainMask,
                                                                appropriateFor: nil,
                                                                create: false)
            if let icloudDirectory = icloudDirectory {
                try FileManager.default.startDownloadingUbiquitousItem(at: icloudDirectory)
            }
            
            savePath = (icloudDirectory ?? documentDirectory).appendingPathComponent("userData")
        } catch let error {
            fatalError("Couldn't create save state data with error: \(error)")
        }
        
        if let data = try? Data(contentsOf: savePath),
            let savedState = try? decoder.decode(AppState.self, from: data) {
            self.homeState = savedState.homeState
        } else {
            self.homeState = HomeState()
        }
    }
    
    func archiveState() {
        let homeState = self.homeState
        DispatchQueue.global().async {
            let movies = homeState.orderItems
            var savingState = self
            savingState.homeState.orderItems = movies
            guard let data = try? encoder.encode(savingState) else {
                return
            }
            do {
                try data.write(to: savePath)
            } catch let error {
                print("Error while saving app state :\(error)")
            }
        }
       
    }
    
    func sizeOfArchivedState() -> String {
        do {
            let resources = try savePath.resourceValues(forKeys:[.fileSizeKey])
            let formatter = ByteCountFormatter()
            formatter.allowedUnits = .useKB
            formatter.countStyle = .file
            return formatter.string(fromByteCount: Int64(resources.fileSize ?? 0))
        } catch {
            return "0"
        }
    }
    
    #if DEBUG
    init(homeState: HomeState) {
        self.homeState = homeState
    }
    #endif
}
