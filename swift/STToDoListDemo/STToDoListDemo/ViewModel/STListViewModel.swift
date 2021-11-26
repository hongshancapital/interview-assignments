//
//  STListViewModel.swift
//  STToDoListDemo
//
//  Created by song on 2021/11/23.
//

import UIKit

class STListViewModel: NSObject {
    
    private var titleDic: [String: String] = [String: String]()
    private var dataSources: [[STListModel]] = [[STListModel]]()
    private var filteredModels: [[STListModel]] = [[STListModel]]()
    private let concurrentQueue = DispatchQueue(label: "STListViewModel", attributes: .concurrent)
    
    override init() {
        super.init()
    }
    
    func modify(content: String, indexPath: IndexPath?) {
        if let newIndexPath = indexPath {
            if self.dataSources.count > newIndexPath.section {
                var contentSources = self.dataSources[newIndexPath.section]
                var model = contentSources[newIndexPath.row]
                model.content = content
                contentSources[newIndexPath.row] = model
                self.dataSources[newIndexPath.section] = contentSources
            }
        }
    }
    
    func addToDoList(title: String, content: String) {
        self.addToDoList(title: title, content: content, isAdd: true, indexPath: nil)
    }
    
    func addToDoList(title: String, content: String, isAdd: Bool, indexPath: IndexPath?) {
        guard title.count > 0 else { return }
        
        var contentModel = STListModel()
        contentModel.content = content
        contentModel.title = title
        
        if isAdd {
            var needAddNewModel = true
            if let pos = titleDic[title], pos.count > 0 {
                if let index = Int(pos), index >= 0 {
                    if self.dataSources.count > index {
                        needAddNewModel = false
                        var contentSources = self.dataSources[index]
                        contentSources.append(contentModel)
                        self.dataSources[index] = contentSources
                    }
                }
            }
            
            if needAddNewModel {
                var contentSources: [STListModel] = [STListModel]()
                contentSources.append(contentModel)
                dataSources.append(contentSources)
                titleDic[title] = String(dataSources.count - 1)
            }
        } else {
            if let newIndexPath = indexPath, self.dataSources.count > newIndexPath.section {
                var contentSources = self.dataSources[newIndexPath.section]
                var hasExist = false
                for model in contentSources {
                    if model.content == content {
                        hasExist = true
                        break
                    }
                }
                if !hasExist {
                    contentSources.append(contentModel)
                    self.dataSources[newIndexPath.section] = contentSources
                }
            }
        }
        
        for (i, sources) in self.dataSources.enumerated() {
            var tempSources: [STListModel] = [STListModel]()
            for listModel in sources {
                if listModel.isSelected {
                    tempSources.insert(listModel, at: tempSources.count)
                } else {
                    tempSources.insert(listModel, at: 0)
                }
            }
            self.dataSources[i] = tempSources
        }
    }
    
    func numberOfSections(isFiltering: Bool) -> Int {
        if isFiltering {
            return self.filteredModels.count
        }
        return self.dataSources.count
    }
    
    func numberOfRowsInSection(section: Int, isFiltering: Bool) -> Int {
        if isFiltering {
            if self.filteredModels.count > section {
                return self.filteredModels[section].count
            }
        } else {
            if self.dataSources.count > section {
                return self.dataSources[section].count
            }
        }
        return 0;
    }
    
    func cellForRowAt(section: Int, row: Int, isFiltering: Bool) -> STListModel {
        if isFiltering {
            if self.filteredModels.count > section {
                let sources = self.filteredModels[section]
                if sources.count > row {
                    return sources[row]
                }
            }
        } else {
            if self.dataSources.count > section {
                let sources = self.dataSources[section]
                if sources.count > row {
                    return sources[row]
                }
            }
        }
        return STListModel()
    }
    
    func didSelectRowAt(section: Int, row: Int, model: STListModel) {
        concurrentQueue.sync {
            if self.dataSources.count > section {
                var sources = self.dataSources[section]
                if sources.count > row {
                    sources[row] = model
                    var tempSources: [STListModel] = [STListModel]()
                    for listModel in sources {
                        if listModel.isSelected {
                            tempSources.insert(listModel, at: tempSources.count)
                        } else {
                            tempSources.insert(listModel, at: 0)
                        }
                    }
                    self.dataSources[section] = tempSources
                }
            }
        }
    }
    
    func viewForHeaderInSection(section: Int) -> String {
        if self.dataSources.count > section {
            let sources = self.dataSources[section]
            if sources.count > 0 {
                return sources[0].title
            }
        }
        return ""
    }
    
    func filteredModels(searchText: String) {
        filteredModels.removeAll()
        for sources in self.dataSources {
            var tempModels: [STListModel] = [STListModel]()
            for model in sources {
                if model.content.lowercased().contains(searchText) {
                    tempModels.append(model)
                }
            }
            if tempModels.count > 0 {
                filteredModels.append(tempModels)
            }
        }
    }
    
    func filterGroupName(row: Int) -> String {
        if self.dataSources.count > row {
            let sources = self.dataSources[row]
            if sources.count > 0 {
                return sources[0].title
            }
        }
        return ""
    }
}
