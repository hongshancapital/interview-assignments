//
//  AppsViewModel.swift
//  Demo
//
//  Created by Xiaoping Tang on 2023/4/9.
//

import Foundation
import Combine
import CoreData

class AppsViewModel: ObservableObject {
    enum Constants {
        static let pageSize = 15
    }
    
    @Published var viewState: ViewState = .refreshing
    @Published var apps: [AppModel] = []
    let appService: AppServiceProtocol
    var bag: Set<AnyCancellable> = Set<AnyCancellable>()
    
    let requestMoreSubject: PassthroughSubject<Void, Never> = .init()
    
    //db
    let coreDataStack: CoreDataStack
    
    var showLoadingMore: Bool {
        get {
            apps.count >= Constants.pageSize
        }
    }
    
    // WARNING:: 模拟两页后就没有数据了
    var noMoreData: Bool {
        get {
            pageIndex > 2
        }
    }
    
    private var pageIndex = 0

    init(appService: AppServiceProtocol, coreDataStack: CoreDataStack) {
        self.appService = appService
        self.coreDataStack = coreDataStack
        
        requestMoreSubject.receive(on: DispatchQueue.main)
            .throttle(for: .seconds(3), scheduler: RunLoop.main, latest: false)
            .sink {
                self.doLoadMore()
            }
            .store(in: &bag)
    }
    
    func favoriteAppToggle(app: AppModel) {
        if let index = apps.firstIndex(where: { $0.id == app.id }) {
            
            if app.favorite {
                // delete
                let fetchRequest: NSFetchRequest<AppFavoriteEntity> = AppFavoriteEntity.fetchRequest()
                fetchRequest.predicate = NSPredicate(format: "appId == %@", "\(app.id)")
                do {
                    let objects = try coreDataStack.managedContext.fetch(fetchRequest)
                    for object in objects {
                        coreDataStack.managedContext.delete(object)
                    }
                } catch {
                    
                }
            } else {
                // save
                let entity = AppFavoriteEntity(context: coreDataStack.managedContext)
                entity.setValue("\(app.id)", forKey: #keyPath(AppFavoriteEntity.appId))
                entity.setValue(Date(), forKey: #keyPath(AppFavoriteEntity.createDate))
            }
            
            coreDataStack.saveContext()
            
            apps[index].favorite.toggle()
            objectWillChange.send()
        }
    }
    
    func filterFavortedAppIds(appIds: [Int]) -> [Int] {
        let fetch: NSFetchRequest<AppFavoriteEntity> = AppFavoriteEntity.fetchRequest()
        let sortById = NSSortDescriptor(key: #keyPath(AppFavoriteEntity.appId), ascending: false)
        fetch.sortDescriptors = [sortById]
        fetch.predicate = NSPredicate(format: "appId IN %@", appIds)
        
        var favoritedAppIds: [Int] = []
        do {
            let results = try coreDataStack.managedContext.fetch(fetch).compactMap { Int($0.appId ?? "") }
            favoritedAppIds.append(contentsOf: results)
        } catch {
            
        }
        return favoritedAppIds
    }
    
    func checkFavorate(items: [AppModel]) {
        let appIds = items.map { $0.id }
        let favoritedAppIds = filterFavortedAppIds(appIds: appIds)
        
        items.forEach { item in
            if favoritedAppIds.contains(item.id) {
                item.favorite = true
            }
        }
    }
    
    func refresh() async {
        guard self.viewState != .refreshing else {
            return
        }
        self.pageIndex = 0
        DispatchQueue.main.async {
            self.viewState = .refreshing
        }
        let result = await appService.getApps(pageIndex: pageIndex, pageSize: Constants.pageSize)
        switch result {
        case .success(let apps):
            DispatchQueue.main.async {
                self.apps.removeAll()
                self.checkFavorate(items: apps)
                self.apps.append(contentsOf: apps)
                self.objectWillChange.send()
            }
        case .failure(_):
            break
        }
        DispatchQueue.main.async {
            self.viewState = .idle
        }
    }
    
    public func loadMore() {
        guard self.viewState != .loadMore || self.viewState != .refreshing, !self.noMoreData else {
            return
        }
        
        requestMoreSubject.send()
    }
    
    private func doRequestApp() {
        let startDate = Date()
        
        appService.requestApps(pageIndex: pageIndex, pageSize: Constants.pageSize)
            .receive(on: DispatchQueue.main)
            .sink { complection in
                self.viewState = .idle
                switch complection {
                case .finished:
                    break
                case .failure(_):
                    break
                }
            } receiveValue: { values in
                let interval = Date().timeIntervalSince(startDate)
                let delay = max(0, 3 - interval)
                DispatchQueue.main.asyncAfter(deadline: .now() + delay) {
                    if self.viewState == .refreshing {
                        self.apps.removeAll()
                    }
                    self.checkFavorate(items: values)
                    self.apps.append(contentsOf: values)
                    self.objectWillChange.send()
                }
            }
            .store(in: &bag)
    }
    
    public func doRefresh() {
        self.pageIndex = 0
        self.viewState = .refreshing
        doRequestApp()
    }
    
    private func doLoadMore() {
        self.pageIndex += 1
        self.viewState = .loadMore
        doRequestApp()
    }
}
