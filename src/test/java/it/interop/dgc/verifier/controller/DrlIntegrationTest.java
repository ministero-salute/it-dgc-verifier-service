package it.interop.dgc.verifier.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import it.interop.dgc.verifier.config.DrlCFG;
import it.interop.dgc.verifier.entity.DeltaETY;
import it.interop.dgc.verifier.entity.SnapshotETY;
import it.interop.dgc.verifier.entity.dto.DIDTO;
import it.interop.dgc.verifier.testdata.BusinessRulesTestHelper;

@SpringBootTest()
@AutoConfigureMockMvc
class DrlIntegrationTest { 

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    DrlCFG drlCFG;

    @BeforeEach
    void clearRepositoryData() {
        mongoTemplate.remove(
                new Query(),
                BusinessRulesTestHelper.SNAPSHOT_TEST_COLLECTION);
        
        mongoTemplate.remove(
                new Query(),
                BusinessRulesTestHelper.DELTA_TEST_COLLECTION);
    }


    @Test
    void testControllerDRL() throws Exception {
//        drlCFG.setNumMaxItemInChunk(2);
//        
//        mockMvc
//        .perform(get("/v1/dgc/drl"))
//        .andExpect(status().is4xxClientError())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON)); 
//
//        mockMvc
//        .perform(get("/v1/dgc/drl?version=-1"))
//        .andExpect(status().is4xxClientError())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON)); 
//
//
//        List<String> revokedUCVI = new ArrayList<>();
//        for(int i=0; i<5; i++) {
//            revokedUCVI.add(UUID.randomUUID().toString());
//        }
//        SnapshotETY snap = SnapshotETY.builder().version(1L).creationDate(new Date()).revokedUcvi(revokedUCVI).flag_archived(false).build();
//        mongoTemplate.save(snap,BusinessRulesTestHelper.SNAPSHOT_TEST_COLLECTION);
//
//        mockMvc
//        .perform(get("/v1/dgc/drl"))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON)); 
//        
//        mockMvc
//        .perform(get("/v1/dgc/drl?version=1"))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON)); 
    }
    
    @Test
    void testControllerCheckDRL() throws Exception {
//        drlCFG.setNumMaxItemInChunk(2);
//        
//        mockMvc
//        .perform(get("/v1/dgc/drl/check"))
//        .andExpect(status().is4xxClientError())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON)); 
//
//        mockMvc
//        .perform(get("/v1/dgc/drl/check?version=-1"))
//        .andExpect(status().is4xxClientError())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON)); 
//
//
//        List<String> revokedUCVI = new ArrayList<>();
//        for(int i=0; i<5; i++) {
//            revokedUCVI.add(UUID.randomUUID().toString());
//        }
//        SnapshotETY snap = SnapshotETY.builder().version(1L).creationDate(new Date()).revokedUcvi(revokedUCVI).flag_archived(false).build();
//        mongoTemplate.save(snap,"snapshot");
//
//        mockMvc
//        .perform(get("/v1/dgc/drl/check"))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON)); 
    }
    
    @Test
    void testControllerDeltaDRL() throws Exception {
//        drlCFG.setNumMaxItemInChunk(2);
//          
//        List<String> revokedUCVISnap = new ArrayList<>();
//        List<String> revokedUCVI = new ArrayList<>();
//        List<String> revokedUCVI1 = new ArrayList<>();
//        for(int i=0; i<5; i++) {
//            revokedUCVISnap.add(UUID.randomUUID().toString());
//            revokedUCVI.add(UUID.randomUUID().toString());
//            revokedUCVI1.add(UUID.randomUUID().toString());
//            
//        }
// 
//        SnapshotETY snap = SnapshotETY.builder().version(2L).creationDate(new Date()).revokedUcvi(revokedUCVISnap).flag_archived(false).build();
//        mongoTemplate.save(snap,BusinessRulesTestHelper.SNAPSHOT_TEST_COLLECTION);
//        
//        DIDTO diDTO = new DIDTO();
//        diDTO.setInsertions(revokedUCVI);
//        diDTO.setDeletions(revokedUCVI1);
//        DeltaETY delta = DeltaETY.builder().fromVersion(1L).toVersion(2L).delta(diDTO).build();
//        mongoTemplate.save(delta,BusinessRulesTestHelper.DELTA_TEST_COLLECTION);
// 
//        mockMvc
//        .perform(get("/v1/dgc/drl?version=1"))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//        
        
    }
    
    @Test
    void testControllerDeltaCheckDRL() throws Exception {
//        drlCFG.setNumMaxItemInChunk(2);
//          
//        List<String> revokedUCVISnap = new ArrayList<>();
//        List<String> revokedUCVI = new ArrayList<>();
//        List<String> revokedUCVI1 = new ArrayList<>();
//        for(int i=0; i<5; i++) {
//            revokedUCVISnap.add(UUID.randomUUID().toString());
//            revokedUCVI.add(UUID.randomUUID().toString());
//            revokedUCVI1.add(UUID.randomUUID().toString());
//            
//        }
// 
//        SnapshotETY snap = SnapshotETY.builder().version(2L).creationDate(new Date()).revokedUcvi(revokedUCVISnap).flag_archived(false).build();
//        mongoTemplate.save(snap,BusinessRulesTestHelper.SNAPSHOT_TEST_COLLECTION);
//        
//        DIDTO diDTO = new DIDTO();
//        diDTO.setInsertions(revokedUCVI);
//        diDTO.setDeletions(revokedUCVI1);
//        DeltaETY delta = DeltaETY.builder().fromVersion(1L).toVersion(2L).delta(diDTO).build();
//        mongoTemplate.save(delta,BusinessRulesTestHelper.DELTA_TEST_COLLECTION);
// 
//        mockMvc
//        .perform(get("/v1/dgc/drl/check?version=1"))
//        .andExpect(status().isOk())
//        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
//        
//        
    }
   

}
